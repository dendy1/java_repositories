package org.nebezdari.repositories;

import org.nebezdari.contracts.Contract;

import java.util.Optional;

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
     */
    @Override
    public Contract set(int index, Contract item) {
        return contracts[index] = item;
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
}
