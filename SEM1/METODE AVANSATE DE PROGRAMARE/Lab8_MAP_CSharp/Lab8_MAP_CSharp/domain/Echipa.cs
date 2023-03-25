namespace Lab8_MAP_CSharp;

public class Echipa : Entity<int>
{
    public String nume { get; set; }

    public Echipa(string nume)
    {
        this.nume = nume;
    }

    public override string ToString()
    {
        return base.ToString() + "," + nume;
    }
}