package org.nebezdari.IO;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.nebezdari.DI.AutoInjectable;
import org.nebezdari.Gender;
import org.nebezdari.Person;
import org.nebezdari.contracts.*;
import org.nebezdari.repositories.IRepository;
import org.nebezdari.validators.IValidator;
import org.nebezdari.validators.Message;
import org.nebezdari.validators.Status;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContractCSVParser implements IParser<Contract> {

    @AutoInjectable(clazz = IValidator.class)
    private static final List<IValidator> validatorsList = new ArrayList<>();

    public void addValidator(IValidator validator) {
        validatorsList.add(validator);
    }

    public List<IValidator> getValidatorsList() { return validatorsList; }

    private Boolean personValidation = true;
    private Boolean contractValidation = true;

    public boolean getPersonValidation() {
        return personValidation;
    }

    public void setPersonValidation(boolean validation) {
        personValidation = validation;
    }

    public boolean getContractValidation() {
        return contractValidation;
    }

    public void setContractValidation(boolean validation) {
        contractValidation = validation;
    }

    private <T extends IValidator<R>, R> boolean objectValid(List<T> validatorsList, R objectForValidation) {
        List<Message> validationMessages = doValidation(validatorsList, objectForValidation);
        printValidationMessages(validationMessages);

        for (Message m: validationMessages) {
            if (m.getStatus().equals(Status.ERROR))
                return false;
        }

        return true;
    }

    private <T extends IValidator<R>, R> List<Message> doValidation(List<T> validatorsList, R objectForValidation) {
        List<Message> messages = new ArrayList<>();
        for (IValidator<R> validator : validatorsList) {
            if (objectForValidation.getClass() == validator.getAppliableFor()) {
                Message message = validator.validate(objectForValidation);
                messages.add(message);
            }
        }
        return messages;
    }

    private void printValidationMessages(List<Message> messages) {
        for (Message message: messages) {
            String messageString = String.format("<STATUS: %s> %s", message.getStatus(), message.getMessage());
            System.out.println(messageString);
        }
    }

    @Override
    public void fromFileToRepository(IRepository<Contract> repository, String filePath) throws IOException {
        // Creating Reader
        Reader reader = Files.newBufferedReader(Paths.get(filePath));

        // Creating CSVParser
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();

        // Creating CSVReader
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        // Read one record at a time
        String[] record;

        try {
            while ((record = csvReader.readNext()) != null) {

                try {
                    Person owner = parsePerson(repository, record);
                    if (personValidation) {
                        if (!objectValid(validatorsList, owner)) {
                            continue;
                        }
                    }

                    Contract contract = parseContract(owner, record);
                    if (contractValidation) {
                        if (objectValid(validatorsList, contract)) {
                            repository.add(contract);
                        }
                    }
                    else {
                        repository.add(contract);
                    }
                }
                catch (ContractCSVParserException ex) {
                    ex.printStackTrace();
                }
            }

            csvReader.close();
            reader.close();
        }
        catch (CsvValidationException ex) {
            ex.printStackTrace();
        }
    }

    private Person parsePerson(IRepository<Contract> repository, String[] data) {
        String firstName = data[2];
        String secondName = data[3];
        String middleName = data[4];
        Gender gender = Gender.valueOf(data[5]);
        LocalDate dateOfBirth = LocalDate.parse(data[6], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long passportData = Long.parseLong(data[7]);

        return checkPersonByPassport(repository, passportData).orElse(new Person(
                Person.atomicLong.getAndIncrement(),
                firstName,
                secondName,
                middleName,
                dateOfBirth,
                gender,
                passportData
        ));
    }

    private Optional<Person> checkPersonByPassport(IRepository<Contract> repository, Long passportData) {
        Optional<Contract> found = repository.findFirst(contract -> contract.getOwner().getPassportData().equals(passportData));
        return found.map(Contract::getOwner);
    }

    private Contract parseContract(Person owner, String[] data) throws ContractCSVParserException {
        LocalDate startDate = LocalDate.parse(data[0], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        LocalDate endDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long contractNumber = Long.parseLong(data[8]);
        String contractType = data[9];
        String contractData = data[10];

        return createContract(startDate, endDate, contractNumber, owner, contractType, contractData);
    }

    private Contract createContract(LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, String contractType, String contractData) throws ContractCSVParserException {
        switch (contractType.toLowerCase()) {
            case "internet":
                return parseInternetContract(startDate, endDate, contractNumber, owner, contractType, contractData);
            case "mobile":
                return parseMobileContract(startDate, endDate, contractNumber, owner, contractType, contractData);
            case "tv":
                return parseTVContract(startDate, endDate, contractNumber, owner, contractType, contractData);
        }

        throw new ContractCSVParserException("Invalid contract type");
    }

    private InternetContract parseInternetContract(LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, String contractType, String contractData) throws ContractCSVParserException {
        float speedValue = parseSpeedValue(contractData);

        return new InternetContract(
                Contract.atomicLong.getAndIncrement(),
                startDate,
                endDate,
                contractNumber,
                owner,
                convertSpeedToBps(speedValue, contractData)
        );
    }

    private float parseSpeedValue(String internetData) throws ContractCSVParserException {
        Pattern pattern = Pattern.compile("([0-9]*[.])?[0-9]+");
        Matcher matcher = pattern.matcher(internetData);

        if (matcher.find()) {
            try {
                return Float.parseFloat(matcher.group(0));
            }
            catch (NumberFormatException ex) {
                throw new ContractCSVParserException("Invalid speed value data (must be float) for MobileContract");
            }
        } else {
            throw new ContractCSVParserException("Invalid speed data for InternetContract");
        }
    }

    private float convertSpeedToBps(float speedValue, String internetData) throws ContractCSVParserException {
        Pattern pattern = Pattern.compile("[MKG]?bps");
        Matcher matcher = pattern.matcher(internetData);
        String metric;

        if (matcher.find()) {
            metric = matcher.group(0).toLowerCase();
            switch (metric) {
                case "bps":
                    return speedValue;
                case "kbps":
                    return speedValue * 1024;
                case "mbps":
                    return speedValue * 1024 * 1024;
                case "gbps":
                    return speedValue * 1024 * 1024 * 1024;
                default:
                    throw new ContractCSVParserException("Invalid speed metric for InternetContract");
            }
        } else {
            throw new ContractCSVParserException("Invalid contract data for InternetContract");
        }
    }

    private MobileContract parseMobileContract(LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, String contractType, String contractData) throws ContractCSVParserException {
        int minutes = parseMinutesData(contractData);
        int sms = parseSMSData(contractData);
        float gb = parseMobileInternetData(contractData);

        return new MobileContract(
                Contract.atomicLong.getAndIncrement(),
                startDate,
                endDate,
                contractNumber,
                owner,
                minutes,
                sms,
                gb
        );
    }

    private Integer parseSMSData(String mobileData) throws ContractCSVParserException {
        // Parsing sms traffic
        Pattern pattern = Pattern.compile("\\d+sms");
        Matcher matcher = pattern.matcher(mobileData);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(0).substring(0, matcher.group(0).length() - 3));
            }
            catch (NumberFormatException ex) {
                throw new ContractCSVParserException("Invalid sms value data (must be integer) for MobileContract");
            }
        } else {
            throw new ContractCSVParserException("Invalid sms data for MobileContract");
        }
    }

    private Integer parseMinutesData(String mobileData) throws ContractCSVParserException {
        // Parsing minutes traffic
        Pattern pattern = Pattern.compile("\\d+min");
        Matcher matcher = pattern.matcher(mobileData);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(0).substring(0, matcher.group(0).length() - 3));
            }
            catch (NumberFormatException ex) {
                throw new ContractCSVParserException("Invalid minutes value data (must be integer) for MobileContract");
            }
        } else {
            throw new ContractCSVParserException("Invalid minutes data for MobileContract");
        }
    }

    private Float parseMobileInternetData(String mobileData) throws ContractCSVParserException {
        // Parsing internet traffic
        Pattern pattern = Pattern.compile("\\d+gb");
        Matcher matcher = pattern.matcher(mobileData);
        if (matcher.find()) {
            try {
                return Float.parseFloat(matcher.group(0).substring(0, matcher.group(0).length() - 2));
            }
            catch (NumberFormatException ex) {
                throw new ContractCSVParserException("Invalid  mobile internet value data (must be float) for MobileContract");
            }
        } else {
            throw new ContractCSVParserException("Invalid mobile internet data for MobileContract");
        }
    }

    private TVContract parseTVContract(LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, String contractType, String contractData) throws ContractCSVParserException {
        try {
            TVPackage tvPackage = TVPackage.valueOf(contractData);
            return new TVContract(Contract.atomicLong.getAndIncrement(), startDate, endDate, contractNumber, owner, tvPackage);
        }
        catch (IllegalArgumentException ex) {
            throw new ContractCSVParserException("Invalid TVPackage data for TVContract");
        }
    }
}