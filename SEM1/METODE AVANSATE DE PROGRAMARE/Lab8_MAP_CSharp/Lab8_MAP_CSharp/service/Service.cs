using Lab8_MAP_CSharp.repository;
using Lab8_MAP_CSharp.repository.file;

namespace Lab8_MAP_CSharp.service;

public class Service
{
    private Repository<int, Echipa> echipe;
    private Repository<int, Jucator> jucatori;
    private Repository<Tuple<Tuple<int, int>, DateTime>, Meci> meciuri;
    private Repository<Tuple<int, Tuple<Tuple<int, int>, DateTime>>, JucatorActiv> jucatoriActivi;

    public Service(Repository<int, Echipa> echipe, Repository<int, Jucator> jucatori,
        Repository<Tuple<Tuple<int, int>, DateTime>, Meci> meciuri, JucatorActivFileRepository jucatoriActivi)
    {
        this.echipe = echipe;
        this.jucatori = jucatori;
        this.meciuri = meciuri;
        this.jucatoriActivi = jucatoriActivi;
    }

    public void cerinta1(int idEchipa)
    {
        jucatori.findAll().Where(x => x.idEchipa == idEchipa).ToList().ForEach(Console.WriteLine);
    }

    public void cerinta2(int idEchipa, Tuple<Tuple<int, int>, DateTime> idMeci)
    {
        jucatoriActivi.findAll()
            .Where(x => Equals(x.id.Item2, idMeci) &&
                        Equals(jucatori.findOne(x.id.Item1).idEchipa, idEchipa))
            .ToList().ForEach(Console.WriteLine);
    }

    public void cerinta3(DateTime start, DateTime end)
    {
        meciuri.findAll().Where(x => x.id.Item2 >= start && x.id.Item2 <= end).ToList().ForEach(Console.WriteLine);
    }

    public void cerinta4(Tuple<Tuple<int, int>, DateTime> idMeci)
    {
        Console.WriteLine("Scorul de la meciul introdus este: " + jucatoriActivi.findAll()
            .Where(x => Equals(x.id.Item2, idMeci)).Sum(x => x.nrPuncteInscrise));
    }

    public List<Echipa> getEchipe()
    {
        return echipe.findAll().ToList();
    }

    public List<Jucator> getJucatori()
    {
        return jucatori.findAll().ToList();
    }

    public List<Meci> getMeciuri()
    {
        return meciuri.findAll().ToList();
    }

    public List<JucatorActiv> getJucatoriActivi()
    {
        return jucatoriActivi.findAll().ToList();
    }
}