import org.nebezdari.IO.ContractCSVParser;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ContractRepository repository = new ContractRepository();
        try {
            ContractCSVParser csvParser = new ContractCSVParser();
            csvParser.fromFileToRepository(repository, "C:/contracts.csv");

            for (Contract c: repository.getAll()) {
                System.out.println(c);
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
