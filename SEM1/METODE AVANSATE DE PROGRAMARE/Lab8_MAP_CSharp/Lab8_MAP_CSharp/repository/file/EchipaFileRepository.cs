namespace Lab8_MAP_CSharp.repository.file;

public class EchipaFileRepository : AbstractFileRepository<int, Echipa>
{
    public EchipaFileRepository(string filename) : base(filename)
    {
    }
    
    public override Echipa extractEntity(string line)
    {
        string[] fields = line.Split(',');
        Echipa echipa = new Echipa(fields[1]);
        echipa.id = Int32.Parse(fields[0]);
        return echipa;
    }
}