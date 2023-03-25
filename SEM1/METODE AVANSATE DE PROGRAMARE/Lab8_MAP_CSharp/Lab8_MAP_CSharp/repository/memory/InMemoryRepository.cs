namespace Lab8_MAP_CSharp.repository.memory;

public class InMemoryRepository<ID, E> : Repository<ID, E> where E : Entity<ID>
{
    protected IDictionary<ID, E> entities;

    public InMemoryRepository()
    {
        entities = new Dictionary<ID, E>();
    }

    public E findOne(ID id)
    {
        return entities[id];
    }

    public IEnumerable<E> findAll()
    {
        return entities.Values.ToList<E>();
    }
}