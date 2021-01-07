import org.nebezdari.DI.Injector;
import org.nebezdari.DI.InjectorException;
import org.nebezdari.IO.ContractCSVParser;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ContractRepository repository = new ContractRepository();
        ContractCSVParser csvParser = new ContractCSVParser();

        try {
            Injector.Inject(csvParser);
            csvParser.fromFileToRepository(repository, "C:/contracts.csv");

            for (Contract c: repository.getAll()) {
                System.out.println(c);
            }
        }
        catch (IOException | InjectorException exception) {
            exception.printStackTrace();
        }
    }
}
