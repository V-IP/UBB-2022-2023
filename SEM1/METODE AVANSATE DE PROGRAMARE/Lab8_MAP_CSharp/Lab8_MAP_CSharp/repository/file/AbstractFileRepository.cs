using Lab8_MAP_CSharp.repository.memory;

namespace Lab8_MAP_CSharp.repository.file;

public abstract class AbstractFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
{
    private string filename;

    public AbstractFileRepository(string filename)
    {
        this.filename = filename;
            loadData();
    }

    private void loadData()
    {
        List<E> list = new List<E>();
        using (StreamReader sr = new StreamReader(filename))
        {
            string s;
            while ((s = sr.ReadLine()) != null)
            {
                E entity = extractEntity(s);
                list.Add(entity);
            }
        }
        list.ForEach(x=>entities[x.id]=x);
    }

    public abstract E extractEntity(string line);
}