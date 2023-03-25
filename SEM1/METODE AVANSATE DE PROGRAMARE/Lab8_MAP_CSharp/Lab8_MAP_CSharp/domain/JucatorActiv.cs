namespace Lab8_MAP_CSharp;

public enum Tip
{
    Participant,
    Rezerva
}

public class JucatorActiv : Entity<Tuple<int, Tuple<Tuple<int, int>, DateTime>>>
{
    public int nrPuncteInscrise { get; set; }
    public Tip tip { get; set; }

    public JucatorActiv(int nrPuncteInscrise, Tip tip)
    {
        this.nrPuncteInscrise = nrPuncteInscrise;
        this.tip = tip;
    }

    public override string ToString()
    {
        return base.ToString() + "," + nrPuncteInscrise + "," + tip;
    }

    protected bool Equals(JucatorActiv other)
    {
        return nrPuncteInscrise == other.nrPuncteInscrise && tip == other.tip;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((JucatorActiv)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(nrPuncteInscrise, (int)tip);
    }
}