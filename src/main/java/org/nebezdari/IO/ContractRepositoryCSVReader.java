package org.nebezdari.IO;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvValidationException;
import org.nebezdari.Gender;
import org.nebezdari.Person;
import org.nebezdari.contracts.*;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContractRepositoryCSVReader {
    public static void Read(String filename, ContractRepository repository) throws IOException, CsvValidationException {
        // Creating Reader
        Reader reader = Files.newBufferedReader(Paths.get(filename));

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        while ((record = csvReader.readNext()) != null) {
            LocalDate startDate = LocalDate.parse(record[0], formatter), endDate = LocalDate.parse(record[1], formatter);
            String firstName = record[2], secondName = record[3], middleName = record[4];
            Gender gender = Gender.valueOf(record[5]);
            LocalDate dateOfBirth = LocalDate.parse(record[6], formatter);
            Long passportData = Long.parseLong(record[7]);

            Person person = FindPerson(repository, passportData).orElse(
                    new Person(
                            Person.atomicLong.getAndIncrement(),
                            firstName,
                            secondName,
                            middleName,
                            dateOfBirth,
                            gender,
                            passportData
                    )
            );

            Long contractNumber = Long.parseLong(record[8]);
            String contractType = record[9];
            String contractData = record[10];

            try {
                repository.add(CreateContract(
                        startDate,
                        endDate,
                        contractNumber,
                        person,
                        contractType,
                        contractData)
                );
            }
            catch (Exception ex) {
                ex.printStackTrace();
                csvReader.close();
                reader.close();
            }
        }

        csvReader.close();
        reader.close();
    }

    private static Optional<Person> FindPerson(ContractRepository repository, Long passportData) {
        Optional<Contract> found = repository.findFirst(contract -> contract.getOwner().getPassportData().equals(passportData));
        return found.map(Contract::getOwner);
    }

    private static Contract CreateContract(LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, String contractType, String contractData) throws Exception {
        Pattern pattern;
        Matcher matcher;

        switch (contractType.toLowerCase()) {
            case "internet":
                // Parsing speed value
                pattern = Pattern.compile("([0-9]*[.])?[0-9]+");
                matcher = pattern.matcher(contractData);

                Float value;
                if (matcher.find()) {
                    value = Float.parseFloat(matcher.group(0));
                }
                else {
                    throw new IOException("Invalid contract data for InternetContract");
                }

                // Parsing speed metric (bps, Mbps, Kbps, Gbps)
                pattern = Pattern.compile("[MKG]?bps");
                matcher = pattern.matcher(contractData);
                String metric;

                if (matcher.find()) {
                    metric = matcher.group(0).toLowerCase();
                }
                else {
                    throw new IOException("Invalid contract data for InternetContract");
                }

                switch (metric) {
                    case "bps":
                        return new InternetContract(
                                Contract.atomicLong.getAndIncrement(),
                                startDate,
                                endDate,
                                contractNumber,
                                owner,
                                value
                        );
                    case"kbps":
                        return InternetContract.fromKbps(
                                Contract.atomicLong.getAndIncrement(),
                                startDate,
                                endDate,
                                contractNumber,
                                owner,
                                value
                        );
                    case"mbps":
                        return InternetContract.fromMbps(
                                Contract.atomicLong.getAndIncrement(),
                                startDate,
                                endDate,
                                contractNumber,
                                owner,
                                value
                        );
                    case "gbps":
                        return InternetContract.fromGbps(
                                Contract.atomicLong.getAndIncrement(),
                                startDate,
                                endDate,
                                contractNumber,
                                owner,
                                value
                        );
                    default:
                        throw new Exception("Invalid parsing");
                }
            case "mobile":
                // Parsing sms
                pattern = Pattern.compile("\\d+sms");
                matcher = pattern.matcher(contractData);
                int sms;
                if (matcher.find()) {
                    sms = Integer.parseInt(matcher.group(0).substring(0, matcher.group(0).length() - 3));
                }
                else {
                    throw new IOException("Invalid sms data for MobileContract");
                }

                // Parsing minutes
                pattern = Pattern.compile("\\d+min");
                matcher = pattern.matcher(contractData);
                int minutes;
                if (matcher.find()) {
                    minutes = Integer.parseInt(matcher.group(0).substring(0, matcher.group(0).length() - 3));
                }
                else {
                    throw new IOException("Invalid minutes data for MobileContract");
                }
                // Parsing gigabytes
                pattern = Pattern.compile("\\d+gb");
                matcher = pattern.matcher(contractData);
                float gb;
                if (matcher.find()) {
                    gb = Float.parseFloat(matcher.group(0).substring(0, matcher.group(0).length() - 2));
                }
                else {
                    throw new IOException("Invalid gb data for MobileContract");
                }
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
            case "tv":
                TVPackage tvPackage = TVPackage.valueOf(contractData);
                return new TVContract(
                        Contract.atomicLong.getAndIncrement(),
                        startDate,
                        endDate,
                        contractNumber,
                        owner,
                        tvPackage);
                }

        throw new Exception("");
    }
}
