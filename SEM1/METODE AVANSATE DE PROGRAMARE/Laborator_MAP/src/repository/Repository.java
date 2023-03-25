package repository;

public interface Repository<ID, E> {

    int size();

    void save(E entity);

    void delete(ID id);

    E findOneById(ID id);

    void update(E entity, E newEntity);

    Iterable<E> findAll();
}
