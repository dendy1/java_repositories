package org.nebezdari.IO;

import org.junit.*;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CSVReaderTest {
    private ContractRepository repository;

    /**
     * Создает пустой {@link ContractRepository} для тестирования перед каждым тестом
     */
    @Before
    public void initTest() {
        repository = new ContractRepository();
    }

    /**
     * Очищает репозиторий для тестирования после каждого тестоа
     */
    @After
    public void afterTest() {
        repository = null;
    }

    @Test
    public void ReaderTest() {
        String filename = "C:/contracts.csv";

        try {
            ContractCSVParser csvParser = new ContractCSVParser();
            csvParser.fromFileToRepository(repository, filename);

            for (Contract c: repository.getAll()) {
                System.out.println(c);
            }

            assertEquals(3, repository.size());
        }
        catch (IOException ignored) {

        }
    }
}
