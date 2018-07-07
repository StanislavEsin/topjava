package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.repository.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User("1", "one@gmail.com", "password", Role.ROLE_ADMIN));
        save(new User("2", "two@gmail.com", "password", Role.ROLE_USER));
    }

    @Override
    public User save(final User user) {
        Objects.requireNonNull(user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }

        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(final int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(final int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(final String email) {
        Objects.requireNonNull(email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }
}