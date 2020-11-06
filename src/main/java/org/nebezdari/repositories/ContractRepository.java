package org.nebezdari.repositories;

import org.nebezdari.contracts.Contract;

import java.util.Optional;

public class ContractRepository implements IRepository<Contract> {
    private static final int DEFAULT_CAPACITY = 20;
    private static final int RESIZE_RATE = 2;
    private static final int TRUNC_RATE = 4;

    private Contract[] contracts = new Contract[DEFAULT_CAPACITY];
    private int pointer = 0;

    private void resize_array(int length)
    {
        Contract[] proxy = new Contract[length];
        System.arraycopy(contracts, 0, proxy, 0, pointer);
        contracts = proxy;
    }

    private int indexById(long id)
    {
        for (int i = 0; i < pointer; i++) {
            if (contracts[i].getId() == id) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void add(Contract item) {
        if (pointer == contracts.length) {
            resize_array(contracts.length * RESIZE_RATE);
        }

        contracts[pointer++] = item;
    }

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

    public Optional<Contract> removeById(long id) {
        int index = indexById(id);
        return index < 0 ? Optional.empty() : Optional.of(remove(index));
    }

    @Override
    public void clear() {
        contracts = new Contract[DEFAULT_CAPACITY];
    }

    @Override
    public Contract get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > pointer - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return contracts[index];
    }

    public Optional<Contract> getById(long id) {
        int index = indexById(id);
        return index < 0 ? Optional.empty() : Optional.of(get(index));
    }

    @Override
    public Contract set(int index, Contract item) {
        return contracts[index] = item;
    }

    @Override
    public Contract[] getAll() {
        Contract[] proxy = new Contract[pointer];
        System.arraycopy(contracts, 0, proxy, 0, pointer);
        return proxy;
    }

    @Override
    public int size() {
        return pointer;
    }

    @Override
    public boolean isEmpty() {
        return pointer < 1;
    }
}
