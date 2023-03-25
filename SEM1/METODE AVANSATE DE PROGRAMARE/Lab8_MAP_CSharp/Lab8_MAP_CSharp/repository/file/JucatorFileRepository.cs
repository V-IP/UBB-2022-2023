namespace Lab8_MAP_CSharp.repository.file;

public class JucatorFileRepository : AbstractFileRepository<int, Jucator>
{
    public JucatorFileRepository(string filename) : base(filename)
    {
    }

    public override Jucator extractEntity(string line)
    {
        string[] fields = line.Split(',');
        Jucator jucator = new Jucator(fields[1],fields[2],Int32.Parse(fields[3]));
        jucator.id = Int32.Parse(fields[0]);
        return jucator;
    }
}