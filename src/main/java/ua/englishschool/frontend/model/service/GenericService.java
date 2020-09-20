package ua.englishschool.frontend.model.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<E> {

    Optional<Long> create(E object);

    boolean update(E object);

    List<E> getAll();

    Optional<E> getById(long id);

    boolean delete(long id);

    boolean isExists(long id);
}

