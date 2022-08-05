package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAcc = getById(fromId);
        Optional<Account> toAcc = getById(toId);
        boolean condition = fromAcc.isPresent() && toAcc.isPresent() && amount <= fromAcc.get().amount();
        if (condition) {
            update(new Account(fromAcc.get().id(), fromAcc.get().amount() - amount));
            update(new Account(toAcc.get().id(), toAcc.get().amount() + amount));
        }
        return condition;
    }
}
