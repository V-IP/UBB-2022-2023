using Lab8_MAP_CSharp.repository.file;
using Lab8_MAP_CSharp.service;

namespace Lab8_MAP_CSharp.ui;

public class UI
{
    private Service service = new Service(new EchipaFileRepository("..\\..\\..\\data\\echipe.txt"),
        new JucatorFileRepository("..\\..\\..\\data\\jucatori.txt"),
        new MeciFileRepository("..\\..\\..\\data\\meciuri.txt"),
        new JucatorActivFileRepository("..\\..\\..\\data\\jucatoriActivi.txt"));

    public void cerinta1()
    {
        Console.WriteLine("Introduceti id-ul echipei: ");
        int idEchipa = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Toti jucatorii echipei date sunt:");
        service.cerinta1(idEchipa);
    }

    public void cerinta2()
    {
        Console.WriteLine("Introduceti id-ul echipei cautate: ");
        int idEchipa = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Introduceti id-ul primei echipe din meci: ");
        int idEchipa1 = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Introduceti id-ul celei de-al doilea echipe din meci: ");
        int idEchipa2 = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Introduceti data si ora meciului dupa formatul LL/ZZ/AAAA HH:MM:SS AM(/PM)");
        string data = Console.ReadLine();
        Console.WriteLine("Toti jucatorii activi ai echipei date de la un anumit meci sunt:");
        service.cerinta2(idEchipa,
            new Tuple<Tuple<int, int>, DateTime>(new Tuple<int, int>(idEchipa1, idEchipa2), Convert.ToDateTime(data)));
        Console.WriteLine();
    }

    public void cerinta3()
    {
        Console.WriteLine("Introduceti data (si ora) de inceput dupa formatul LL/ZZ/AAAA (HH:MM:SS) ");
        string start = Console.ReadLine();
        Console.WriteLine("Introduceti data (si ora) de final dupa formatul LL/ZZ/AAAA (HH:MM:SS) ");
        string end = Console.ReadLine();
        Console.WriteLine("Toate meciurile din perioada data");
        service.cerinta3(Convert.ToDateTime(start), Convert.ToDateTime(end));
        Console.WriteLine();
    }

    public void cerinta4()
    {
        Console.WriteLine("Introduceti id-ul primei echipe din meci: ");
        int idEchipa1 = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Introduceti id-ul celei de-a doua echipe din meci: ");
        int idEchipa2 = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Introduceti data si ora meciului dupa formatul LL/ZZ/AAAA HH:MM:SS AM(/PM)");
        string data = Console.ReadLine();
        service.cerinta4(new Tuple<Tuple<int, int>, DateTime>(new Tuple<int, int>(idEchipa1,idEchipa2),Convert.ToDateTime(data)));
        Console.WriteLine();
    }

    public void getEchipe()
    {
        service.getEchipe().ForEach(Console.WriteLine);
    }
    
    public void getJucatori()
    {
        service.getJucatori().ForEach(Console.WriteLine);
    }
    
    public void getMeciuri()
    {
        service.getMeciuri().ForEach(Console.WriteLine);
    }
    
    public void getJucatoriActivi()
    {
        service.getJucatoriActivi().ForEach(Console.WriteLine);
    }
}