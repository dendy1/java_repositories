package org.nebezdari.DI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nebezdari.IO.ContractCSVParser;
import org.nebezdari.contracts.Contract;
import org.nebezdari.repositories.ContractRepository;
import org.nebezdari.sorters.quicksort.QuickSorter;

import java.io.IOException;

import static org.junit.Assert.*;

public class InjectorTest {
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
    public void InjectRepositoryTest() {
        assertNull(repository.getSorter());
        try {
            Injector.Inject(repository);
            assertNotNull(repository.getSorter());
        } catch (InjectorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void InjectCSVParserTest() {
        ContractCSVParser csvParser = new ContractCSVParser();
        assertEquals(csvParser.getValidatorsList().size(), 0);

        try {
            Injector.Inject(csvParser);
            assertTrue(csvParser.getValidatorsList().size() > 0);
        } catch (InjectorException e) {
            e.printStackTrace();
        }
    }
}
