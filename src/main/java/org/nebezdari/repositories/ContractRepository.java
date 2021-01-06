package org.nebezdari.repositories;

import org.nebezdari.DI.AutoInjectable;
import org.nebezdari.contracts.Contract;
import org.nebezdari.sorters.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

/** Класс для инкапсуляции в себе объектов класса Контракт {@link Contract} */
public class ContractRepository implements IRepository<Contract> {
    /** Стандартный размер создаваемого массива */
    private static final int DEFAULT_CAPACITY = 20;

    /** Коэффициент увеличения (уменьшения) размера внутреннего массива при недостатке (избытке) */
    private static final int RESIZE_RATE = 2;

    /** Коэффициент отношения размера {@link ContractRepository#contracts} к {@link ContractRepository#pointer},
     * при котором внутренний массив сократиться в {@link ContractRepository#RESIZE_RATE} раз
     */
    private static final int TRUNC_RATE = 4;

    /** Внутренний массив, в котором хранятся элементы */
    private Contract[] contracts = new Contract[DEFAULT_CAPACITY];

    /** Класс сортировщика для сортировки */
    @AutoInjectable
    private ISorter sorter = new QuickSorter();

    /** Указатель на последний элемент массива */
    private int pointer = 0;

    /**
     * Вспомогательная функция для изменения длины внутреннеого массива {@link ContractRepository#contracts}
     * @param length Конечная длина массива
     */
    private void resize_array(int length) {
        Contract[] proxy = new Contract[length];
        System.arraycopy(contracts, 0, proxy, 0, pointer);
        contracts = proxy;
    }

    /**
     * Вспомогательная функция для поиска индекса контракта во внутреннем массиве {@link ContractRepository#contracts} по ID контракта
     * @param id ID контракта
     * @return возвращает индекс объекта, если удалось его найти, либо же минус единицу (-1) в противном случае.
     */
    private int indexById(long id) {
        for (int i = 0; i < pointer; i++) {
            if (contracts[i].getId() == id) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Функция для добавления контракта в репозиторий.
     * Если при добавлении не осталось свободных мест во внутреннем массиве {@link ContractRepository#contracts},
     * то внутренний массив увеличивается в {@link ContractRepository#RESIZE_RATE} раз
     * @param item Объект, который нужно добавить в массив
     */
    @Override
    public void add(Contract item) {
        if (pointer == contracts.length) {
            resize_array(contracts.length * RESIZE_RATE);
        }

        contracts[pointer++] = item;
    }

    /**
     * Функция для удаления контракта из репозитория по индексу.
     * Если после удаления контракта количество элементов стало в {@link ContractRepository#TRUNC_RATE} раз меньше,
     * чем размер внутреннего массива {@link ContractRepository#contracts},
     * то внутренний массив уменьшается в {@link ContractRepository#RESIZE_RATE} раз для экономии.
     * @param index Индекс элемента репозитория, который нужно удалить
     * @return возвращает удалённый элемент репозитория
     * @throws ArrayIndexOutOfBoundsException выкидывает исключение при указании индекса за границей массива
     */
    @Override
    public Contract remove(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > pointer - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Contract removable = contracts[index];

        if (pointer - index >= 0) {
            System.arraycopy(contracts, index + 1, contracts, index, pointer - index);
        }

        if (pointer * TRUNC_RATE > contracts.length) {
            resize_array(contracts.length / RESIZE_RATE);
        }

        pointer--;
        return removable;
    }

    /**
     * Функция для удаления из репозитория контракта по его ID.
     * Если после удаления контракта количество элементов стало в {@link ContractRepository#TRUNC_RATE} раз меньше,
     * чем размер внутреннего массива {@link ContractRepository#contracts},
     * то внутренний массив уменьшается в {@link ContractRepository#RESIZE_RATE} раз для экономии.
     * @param id ID контракта, который нужно удалить
     * @return возвращает объект Optional с удалённым контрактом внутри, либо же пустой Optional, если по указанному ID контракта не нашлось.
     */
    public Optional<Contract> removeById(long id) {
        int index = indexById(id);
        return index < 0 ? Optional.empty() : Optional.of(remove(index));
    }

    /**
     * Функция для очистки репозитория
     */
    @Override
    public void clear() {
        pointer = 0;
        contracts = new Contract[DEFAULT_CAPACITY];
    }

    /**
     * Функция для получения контракта из репозитория по индексу
     * @param index Индекс элемента репозитория, который нужно получить
     * @return возвращает элемент репозитория по индексу
     * @throws ArrayIndexOutOfBoundsException выкидывает исключение при указании индекса за границей массива
     */
    @Override
    public Contract get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > pointer - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return contracts[index];
    }

    /**
     * Функция для получения контракта из репозитория по его ID
     * @param id ID контракта
     * @return возвращает объект Optional с найденным контрактом внутри, либо же пустой Optional, если по указанному ID контракта не нашлось.
     */
    public Optional<Contract> getById(long id) {
        int index = indexById(id);
        return index < 0 ? Optional.empty() : Optional.of(get(index));
    }

    /**
     * Функция для изменения элемента репозитория
     * @param index Индекс элемента репозитория, который нужно заменить
     * @param item Контракт, который заменит существующий элемент
     * @return возвращает заменённый контракт
     */
    @Override
    public Contract set(int index, Contract item) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > pointer - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Contract old = contracts[index];
        contracts[index] = item;
        return old;
    }

    /**
     * Функция для возвращения всех элементов репозитория
     * @return Массив с элементами репозитория
     */
    @Override
    public Contract[] getAll() {
        Contract[] proxy = new Contract[pointer];
        System.arraycopy(contracts, 0, proxy, 0, pointer);
        return proxy;
    }

    /**
     * Функция для получения количества элементов в репозитории
     * @return Количество элементов в репозитории
     */
    @Override
    public int size() {
        return pointer;
    }

    /**
     * Функция для проверки репозитория на пустоту
     * @return true, если репозиторий пустой, false - в противном случае
     */
    @Override
    public boolean isEmpty() {
        return pointer < 1;
    }

    /**
     * Функция для получения сортировки элементов репозитория
     * @param comparator Класс компаратора с условием сравнения объектов для сортировки
     */
    @Override
    public void sort(Comparator<Contract> comparator) {
        sorter.sort(contracts, pointer, comparator);
    }

    /**
     * Функция для поиска первого контракта в репозитории, удовлетворяющего заданному условию
     * @param predicate Предикат, по которому будет осущетсвляться поиск
     * @return возвращает объект Optional с найденным контрактом внутри, либо же пустой Optional, если по указанному условию контракта не нашлось.
     */
    @Override
    public Optional<Contract> findFirst(Predicate<Contract> predicate) {
        for (int i = 0; i < pointer; i++) {
            if (predicate.test(contracts[i])) {
                return Optional.of(contracts[i]);
            }
        }

        return Optional.empty();
    }

    /**
     * Функция для поиска всех контрактов в репозитории, удовлетворяющих заданному условию
     * @param predicate Предикат, по которому будет осущетсвляться поиск
     * @return возвращает массив объектов найденны контрактов, либо же пустой массив, если по указанному условию контрактов не нашлось.
     */
    @Override
    public Contract[] findAll(Predicate<Contract> predicate) {
        return Arrays.stream(contracts).limit(pointer).filter(predicate).toArray(Contract[]::new);
    }

    /**
     * Функция получения значения поля {@link ContractRepository#sorter}
     * @return возвращает Объект сортировщика
     */
    public ISorter getSorter(ISorter sorter) {
        return this.sorter;
    }

    /**
     * Функция определения Объекта сортировщика {@link ContractRepository#sorter}
     * @param sorter Объект сортировщика
     */
    public void setSorter(ISorter sorter) {
        this.sorter = sorter;
    }
}
