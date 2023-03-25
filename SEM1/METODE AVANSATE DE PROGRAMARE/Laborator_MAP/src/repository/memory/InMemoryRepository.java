package repository.memory;

import domain.Entity;
import repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Map<ID, E> entities;

    public InMemoryRepository() {
        entities = new HashMap<ID, E>();
    }

    public Map<ID, E> getEntities() {
        return entities;
    }

    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public void save(E entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        entities.remove(id);
    }

    @Override
    public E findOneById(ID id) {
        return entities.get(id);
    }

    @Override
    public void update(E entity, E newEntity) {
        entities.remove(entity.getId());
        entities.put(newEntity.getId(), newEntity);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
}
