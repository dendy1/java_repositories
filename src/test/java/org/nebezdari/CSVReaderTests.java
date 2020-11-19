package org.nebezdari;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.*;
import org.nebezdari.IO.ContractRepositoryCSVReader;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;

import java.io.IOException;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CSVReaderTests {
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
            ContractRepositoryCSVReader.Read(filename, repository);

            for (Contract c: repository.getAll()) {
                System.out.println(c);
            }

            assertEquals(3, repository.size());
        }
        catch (IOException | CsvValidationException ignored) {

        }
    }
}
