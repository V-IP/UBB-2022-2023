namespace Lab8_MAP_CSharp.repository;

public interface Repository<ID, E>
{
    E findOne(ID id);
    IEnumerable<E> findAll();
}