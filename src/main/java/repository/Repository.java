package repository;

import java.util.List;

public interface Repository<T> {

    void add(T item);

    void update(T oldItem, T newItem);

    void remove(T item);

    List<T> getAll();
}
