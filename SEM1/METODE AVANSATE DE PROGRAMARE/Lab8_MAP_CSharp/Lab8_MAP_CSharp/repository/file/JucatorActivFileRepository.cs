namespace Lab8_MAP_CSharp.repository.file;

public class
    JucatorActivFileRepository : AbstractFileRepository<Tuple<int, Tuple<Tuple<int, int>, DateTime>>, JucatorActiv>
{
    public JucatorActivFileRepository(string filename) : base(filename)
    {
    }

    public override JucatorActiv extractEntity(string line)
    {
        string[] fields = line.Split(',');
        JucatorActiv jucatorActiv =
            new JucatorActiv(Convert.ToInt32(fields[4]), (Tip)Enum.Parse(typeof(Tip), fields[5]));
        jucatorActiv.id = new Tuple<int, Tuple<Tuple<int, int>, DateTime>>(Convert.ToInt32(fields[0]),
            new Tuple<Tuple<int, int>, DateTime>(
                new Tuple<int, int>(Convert.ToInt32(fields[1]), Convert.ToInt32(fields[2])),
                Convert.ToDateTime(fields[3])));
        return jucatorActiv;
    }
}