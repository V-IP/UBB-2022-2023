using Lab8_MAP_CSharp.repository.file;
using Lab8_MAP_CSharp.service;
using Lab8_MAP_CSharp.ui;

namespace Lab8_MAP_CSharp;

public class Program
{
    public static void ClearScreen()
    {
        for (int i = 0; i < 100; i++)
            Console.WriteLine();
    }

    private static void printMenu()
    {
        string s = "";
        s = s + "\n ------------------------------------ \n";
        s = s + "Alegeti o optiune de mai jos: \n";
        s = s + "\t \n  1. Sa se afiseze toti jucatorii unei echipe dată";
        s = s + "\t \n  2. Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci";
        s = s + "\t \n  3. Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica";
        s = s + "\t \n  4. Sa se determine si sa se afiseze scorul de la un anumit meci";
        s = s + "\t \n 11. Sa se afiseze toate echipele";
        s = s + "\t \n 12. Sa se afiseze toti jucatorii";
        s = s + "\t \n 13. Sa se afiseze toate meciurile";
        s = s + "\t \n 14. Sa se afiseze toti jucatorii activi";
        s = s + "\n";
        s = s + "\t \n 0. Iesire";
        s = s + "\n ------------------------------------";
        Console.WriteLine(s);
    }

    private static void runMenu(UI ui)
    {
        int opt = 1;
        while (opt != 0)
        {
            printMenu();
            Console.WriteLine("Introduceti optiunea: ");
            opt = Convert.ToInt32(Console.ReadLine());
            ClearScreen();

            switch (opt)
            {
                case 0:
                    Console.WriteLine("Exiting...");
                    break;
                case 1:
                    ui.cerinta1();
                    break;
                case 2:
                    ui.cerinta2();
                    break;
                case 3:
                    ui.cerinta3();
                    break;
                case 4:
                    ui.cerinta4();
                    break;
                case 11:
                    ui.getEchipe();
                    break;
                case 12:
                    ui.getJucatori();
                    break;
                case 13:
                    ui.getMeciuri();
                    break;
                case 14:
                    ui.getJucatoriActivi();
                    break;
                default:
                    Console.WriteLine("Introduceti o optiune valabila!");
                    break;
            }
        }
    }

    static void Main(string[] args)
    {
        UI ui = new UI();
        runMenu(ui);
    }
}