package org.nebezdari.repositories;

import org.junit.*;
import org.nebezdari.Gender;
import org.nebezdari.Person;
import org.nebezdari.contracts.*;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Класс, для тестирования функционала класса {@link ContractRepository}
 */
public class ContractRepositoryTest {
    private ContractRepository repository;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before ContractRepositoryTests.class");
    }

    @AfterClass
    public  static void afterClass() {
        System.out.println("After ContractRepositoryTests.class");
    }

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

    /**
     * Метод для создания экземпляра класса {@link Person}
     * @return возврашает созданный объект класса {@link Person}
     */
    private Person createPerson() {
        return new Person(
                0L,
                "Andrei",
                "Borodin",
                "Olegovich",
                LocalDate.of(1999, 12, 27),
                Gender.MALE,
                2019123456L
        );
    }

    /**
     * Метод для создания экземпляра класса {@link InternetContract}
     * @return возвращает созданный объект класса {@link InternetContract}
     */
    private Contract createInternetContract() {
        return InternetContract.fromKbps(1L, LocalDate.of(2020, 1, 20), LocalDate.of(2020, 6, 1), 20001L, createPerson(), 100F);
    }

    /**
     * Метод для создания экземпляра класса {@link MobileContract}
     * @return возвращает созданный объект класса {@link MobileContract}
     */
    private Contract createMobileContract() {
        return new MobileContract(2L, LocalDate.of(2020, 2, 1), LocalDate.of(2020, 4, 1), 20002L, createPerson(), 1000, 500, 40F);
    }

    /**
     * Метод для создания экземпляра класса {@link TVContract}
     * @return возвращает созданный объект класса {@link TVContract}
     */
    private Contract createTVContract() {
        return new TVContract(3L,  LocalDate.of(2020, 6, 1), LocalDate.of(2020, 12, 1), 20003L, createPerson(), TVPackage.PREMIUM);
    }

    /**
     * Метод для тестирования корректности добавления элементов в репозиторий
     */
    @Test
    public void testAddContract() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(internetContract, repository.getById(internetContract.getId()).get());
        assertEquals(mobileContract, repository.getById(mobileContract.getId()).get());
        assertEquals(tvContract, repository.getById(tvContract.getId()).get());
    }

    /**
     * Метод для тестирования корректности удаления элемента из репозитория по индексу
     */
    @Test
    public void testRemoveContract() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(internetContract, repository.remove(0));
        assertEquals(mobileContract, repository.get(0));
        assertEquals(mobileContract, repository.remove(0));
        assertEquals(tvContract, repository.get(0));
        assertEquals(tvContract, repository.remove(0));
    }

    /**
     * Метод для тестирования корректности удаления элемента из репозитория по ID контракта
     */
    @Test
    public void testRemoveContractByID() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(internetContract, repository.removeById(internetContract.getId()).get());
        assertEquals(mobileContract, repository.removeById(mobileContract.getId()).get());
        assertEquals(tvContract, repository.removeById(tvContract.getId()).get());
    }

    /**
     * Метод для тестирования корректности очистки репозитория
     */
    @Test
    public void testClearRepository() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        repository.clear();
        assertEquals(0, repository.size());
    }

    /**
     * Метод для тестирования корректности возвращения количества элементов в репозитории
     */
    @Test
    public void testSizeRepository() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);
        assertEquals(3, repository.size());
    }

    /**
     * Метод для тестирования корректности получения элемента из репозитория по индексу
     */
    @Test
    public void testGetContract() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(internetContract, repository.get(0));
        assertEquals(mobileContract, repository.get(1));
        assertEquals(tvContract, repository.get(2));
    }

    /**
     * Метод для тестирования корректности получения элемента из репозитория по ID контракта
     */
    @Test
    public void testGetContractByID() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(internetContract, repository.getById(internetContract.getId()).get());
        assertEquals(mobileContract, repository.getById(mobileContract.getId()).get());
        assertEquals(tvContract, repository.getById(tvContract.getId()).get());
    }

    /**
     * Метод для тестирования корректности получения элемента из репозитория с некорректным значением индекса
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        Contract internetContract = createInternetContract();

        repository.add(internetContract);
        repository.get(1);
    }

    /**
     * Метод для тестирования корректности замены элемента из репозитория на другой
     */
    @Test
    public void testSetContract() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);

        assertEquals(internetContract, repository.set(0, tvContract));
        assertEquals(tvContract, repository.get(0));
    }

    /**
     * Метод для тестирования корректности замены элемента из репозитория на другой с некорректным значением индекса
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetIndexOutOfBounds() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();

        repository.add(internetContract);
        repository.set(1, mobileContract);
    }


    /**
     * Метод для тестирования корректности поиска первого элемента репозитория по заданному условию
     */
    @Test
    public void testFindFirstContractByID() {
        Contract internetContract = createInternetContract(); // ID = 1
        Contract mobileContract = createMobileContract();     // ID = 2
        Contract tvContract = createTVContract();             // ID = 3

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(mobileContract, repository.findFirst(
                contract -> contract.getId() == 2
        ).get());
    }


    /**
     * Метод для тестирования корректности поиска всех элементов репозитория по заданному условию
     */
    @Test
    public void testFindAllContractByID() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertArrayEquals(new Contract[] {mobileContract, tvContract}, repository.findAll(
                contract -> contract.getId() > 1
        ));
    }


    /**
     * Метод для тестирования корректности сортировки элементов репозитория по ID контракта
     */
    @Test
    public void testSortContractByID() {
        Contract internetContract = createInternetContract();
        Contract mobileContract = createMobileContract();
        Contract tvContract = createTVContract();

        repository.add(tvContract);
        repository.add(mobileContract);
        repository.add(internetContract);

        repository.sort(Comparator.comparing(Contract::getId));

        assertArrayEquals(new Contract[] {internetContract, mobileContract, tvContract }, repository.getAll());
    }
}
