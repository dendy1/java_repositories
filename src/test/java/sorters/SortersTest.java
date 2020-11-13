package sorters;

import org.junit.*;
import org.nebezdari.sorters.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * Класс, для тестирования функционала сортировок {@link ISorter}
 */
public class SortersTest {
    private Integer[] numbers;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before Sorters.class");
    }

    @AfterClass
    public  static void afterClass() {
        System.out.println("After Sorters.class");
    }

    /**
     * Создает заполненный массив с целыми числами для тестирования перед каждым тестом
     */
    @Before
    public void initTest() {
        numbers = new Integer[] {7, 5, 25, 18, 17, 23, 22, 6, 4, 8, 1, 14, 24, 28, 1, 9, 3, 7, 2, 9, 0, 22, 20, 11, 27, 30, 28, 8, 1, 26, 24, 16, 12, 15, 21, 19, 2, 20, 10, 14, 6, 29, 4, 3, 9, 13};
    }

    /**
     * Очищает массив для тестирования после каждого тестоа
     */
    @After
    public void afterTest() {
        numbers = null;
    }

    /**
     * Метод для тестирования корректности сортировки пузырьком
     */
    @Test
    public void testBubbleSorter() {
        Integer[] sorted = new Integer[] {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30};
        new BubbleSorter().sort(numbers, numbers.length, Integer::compareTo);
        assertArrayEquals(sorted, numbers);
    }

    /**
     * Метод для тестирования корректности сортировки вставками
     */
    @Test
    public void testInsertionSorter() {
        Integer[] sorted = new Integer[] {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30};
        new InsertionSorter().sort(numbers, numbers.length, Integer::compareTo);
        assertArrayEquals(sorted, numbers);
    }

    /**
     * Метод для тестирования корректности сортировки выбором
     */
    @Test
    public void testSelectionSorter() {
        Integer[] sorted = new Integer[] {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30};
        new SelectionSorter().sort(numbers, numbers.length, Integer::compareTo);
        assertArrayEquals(sorted, numbers);
    }

    /**
     * Метод для тестирования корректности сортировки слиянием
     */
    @Test
    public void testMergeSorter() {
        Integer[] sorted = new Integer[] {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30};
        new MergeSorter().sort(numbers, numbers.length, Integer::compareTo);
        assertArrayEquals(sorted, numbers);
    }

    /**
     * Метод для тестирования корректности быстрой сортировки
     */
    @Test
    public void testQuickSorter() {
        Integer[] sorted = new Integer[] {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30};
        new QuickSorter().sort(numbers, numbers.length, Integer::compareTo);
        assertArrayEquals(sorted, numbers);
    }

}
