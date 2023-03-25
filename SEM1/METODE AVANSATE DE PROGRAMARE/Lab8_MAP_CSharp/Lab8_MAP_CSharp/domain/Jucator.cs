namespace Lab8_MAP_CSharp;

public class Jucator : Elev
{
    public int idEchipa { get; set; }
    public Jucator(string nume, string scoala, int idEchipa) : base(nume, scoala)
    {
        this.idEchipa = idEchipa;
    }

    public override string ToString()
    {
        return base.ToString() + "," + idEchipa;
    }
}