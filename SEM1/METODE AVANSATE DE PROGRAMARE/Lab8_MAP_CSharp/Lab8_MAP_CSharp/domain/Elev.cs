namespace Lab8_MAP_CSharp;

public class Elev : Entity<int>
{
    public String nume { get; set; }
    public String scoala { get; set; }

    public Elev(string nume, string scoala)
    {
        this.nume = nume;
        this.scoala = scoala;
    }

    public override string ToString()
    {
        return base.ToString() + "," + nume + "," + scoala;
    }
}