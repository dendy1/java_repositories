import com.opencsv.exceptions.CsvValidationException;
import org.nebezdari.IO.ContractRepositoryCSVReader;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ContractRepository repository = new ContractRepository();
        try {
            ContractRepositoryCSVReader.Read("C:/contracts.csv", repository);

            for (Contract c: repository.getAll()) {
                System.out.println(c);
            }
        }
        catch (IOException | CsvValidationException exception) {
            exception.printStackTrace();
        }
    }
}
