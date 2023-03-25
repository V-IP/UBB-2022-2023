namespace Lab8_MAP_CSharp.repository.file;

public class MeciFileRepository : AbstractFileRepository<Tuple<Tuple<int, int>, DateTime>, Meci>
{
    public MeciFileRepository(string filename) : base(filename)
    {
    }

    public override Meci extractEntity(string line)
    {
        string[] fields = line.Split(',');
        Meci meci = new Meci();
        meci.id = new Tuple<Tuple<int, int>, DateTime>(
            new Tuple<int, int>(Convert.ToInt32(fields[0]), Convert.ToInt32(fields[1])),
            Convert.ToDateTime(fields[2]));
        return meci;
    }
}