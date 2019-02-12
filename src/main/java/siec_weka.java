import doobslugiplikow.*;
import propagacjawsteczna.*;
import Perceptron.*;
import propagacjawsteczna.funkcjaaktywacji;
import weka.*;
import java.io.File;
import java.util.Scanner;
import Jfree.*;
import java.util.List;
import mnist.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.category.DefaultCategoryDataset;

import static java.lang.Math.abs;


public class siec_weka {
    //    public static void main(String[] args) {
//
//        float[][] input = { {0,0}, {0,1}, {1,0}, {1,1} };
//        float[] output = {1,1,1,0}; // można sobie wybrać którą "bramkę logiczną ma mimikować"
//
//        Perceptron perceptron = new Perceptron(input, output);
//        perceptron.ucz(0.1f);
//
//        System.out.println("uczenie zakonczone! wynik to");
//
//        System.out.println(perceptron.obliczwyjscie(new float[]{0,0}));
//        System.out.println(perceptron.obliczwyjscie(new float[]{0,1}));
//        System.out.println(perceptron.obliczwyjscie(new float[]{1,0}));
//        System.out.println(perceptron.obliczwyjscie(new float[]{1,1}));
//
//    }
    public static void main(String[] args) throws Exception {

        // zrobić wczytywanie sciezki do pliku i odczyt z pliku



        System.out.printf("Podaj sciezkę do pliku, lub podaj 1 jezeli chcesz zobaczyć test dla bramki XOR\n");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        Scanner odczyt_sciezki = new Scanner(System.in);
        String sciezka = odczyt_sciezki.nextLine();
        float[][] Test_dane;
        float[][] Test_wyniki;
        int ilosc_testow = 4;
        int dlugosc_testow = 2;
        int ilosc_kategorii = 1;
        int ukryte_ile = 3;
        if(sciezka.equals( "1"))
        {
            Test_dane = new float[][] {
                    new float[]{0,0},
                    new float[]{0,1},
                    new float[]{1,0},
                    new float[]{1,1}
            };
            Test_wyniki= new float[][] {
                    new float[]{0},
                    new float[]{1},
                    new float[]{1},
                    new float[]{0}
            };
        }else if(sciezka.equals( "2"))
        {
            System.out.printf("Podaj sciezkę do pliku etykiet\n");
            Scanner odczyt_sciezki_2 = new Scanner(System.in);
            String etykiety = odczyt_sciezki_2.nextLine();
            sciezka = odczyt_sciezki_2.nextLine();
            int[] labels = MnistReader.getLabels(etykiety);
            List<int[][]> images = MnistReader.getImages(sciezka);
            ilosc_testow = images.size();
            dlugosc_testow = images.get(0).length*(images.get(0)[0].length);
            ilosc_testow = ilosc_testow/60;
            Test_dane = new float[ilosc_testow][dlugosc_testow];
            ilosc_kategorii = 10;
            Test_wyniki = new float[ilosc_testow][ilosc_kategorii];
            System.out.printf("%d ilosc testów ",ilosc_testow);
            ukryte_ile = odczyt_sciezki.nextInt();

            for(int it = 0;it<ilosc_testow;it++)
            {
                int i = 0;
                for(int it2 = 0;it2 < images.get(it).length;it2 ++)
                {

                    for(int it3 = 0;it3 < images.get(it)[it2].length;it3++)
                    {
                        Test_dane[it][i++] = funkcjaaktywacji.sigmoid(images.get(it)[it2][it3]);

                    }
                }
            }
            for(int i = 0;i<labels.length/60;i++)
            {
                for(int j = 0;j<ilosc_kategorii;j++)
                {
                    Test_wyniki[i][j] = 0;
                }
                Test_wyniki[i][labels[i]] = 1;


            }




        }else
        {
            Scanner odczyt = new Scanner(new File(sciezka)); /// w pliku powinny być ilość testów - ilość "punktów" w każdym teście i ilosc kategorii
            ilosc_testow = odczyt.nextInt();
            dlugosc_testow = odczyt.nextInt();
            ilosc_kategorii = odczyt.nextInt();
            System.out.printf("Podaj liczbę neuronów w warstwie ukrytej:\n");

            ukryte_ile = odczyt_sciezki.nextInt();
            Test_wyniki = new float[ilosc_testow][ilosc_kategorii];
            Test_dane = new float[ilosc_testow][dlugosc_testow];
            int i = 0;
            int j = 0;
            while (odczyt.hasNextFloat()) {
                System.out.printf("%d,%d\n",i,j);

                Test_dane[i][j++] = funkcjaaktywacji.sigmoid(odczyt.nextFloat());

                if(j == dlugosc_testow)
                {
                    j = 0;
                    i++;
                    if(i == ilosc_testow)
                    {
                        break;
                    }
                }

            }
            i = 0;j=0;
            while (odczyt.hasNextFloat()) {
                System.out.printf("%d,%d\n",i,j);

                Test_wyniki[i][j++] = funkcjaaktywacji.sigmoid(odczyt.nextFloat());

                if(j == ilosc_kategorii)
                {
                    j = 0;
                    i++;
                    if(i == ilosc_testow)
                    {
                        break;
                    }
                }

            }
        }



        //zrobic wczytywanie danych
        Propagacjawsteczna siec = new Propagacjawsteczna(dlugosc_testow, ukryte_ile,ilosc_kategorii);

        for (int it = 0; it < stale.N; it++) {

            for (int i = 0; i < Test_dane.length; i++) {
                siec.ucz(Test_dane[i], Test_wyniki[i], stale.LR, stale.MOMENTUM);
            }

            System.out.println();
            for (int i = 0; i < Test_dane.length; i++) {
                float[] t = Test_dane[i];
                System.out.printf("%d nr\n", it + 1);
                for(int j = 0;j<t.length;j++)
                {
                    //System.out.printf("%.1f ,",t[j]);
                }
                System.out.printf("-->");
                float blad = 0;
                for(int j = 0;j<ilosc_kategorii;j++)
                {
                    float wyn = siec.run(t)[j];
                    System.out.printf("%.1f ,",wyn);
                    blad += abs(Test_wyniki[i][j] - wyn)/ilosc_kategorii;
                }
                dataset.addValue(blad,"blad","iteracja nr "+it);
                System.out.println();
                System.out.println("a powinno zwrocic");
                for(int j = 0;j<ilosc_kategorii;j++)
                {
                    System.out.printf("%.1f ,",Test_wyniki[i][j]);

                }
                System.out.println();
            }

        }

        Jfree chart = new Jfree(
                "Iteracja a bład" ,
                "Iteracja a wartość błędu",dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
        String zbior_do_clusterowania =  odczyt_sciezki.nextLine();
        Scanner odczyt_pliku = new Scanner(zbior_do_clusterowania);
        float[][] Dane = new float[ilosc_testow][dlugosc_testow];
        int i = 0,j = 0;
        while (odczyt_pliku.hasNextFloat())
        {
            Dane[i][j++] = odczyt_pliku.nextFloat();
            if(dlugosc_testow == j)
            {
                j=0;
                i++;
            }


        }
        for (int i1 = 0; i1 < Test_dane.length; i1++) {
            float[] t = Dane[i1];
            for(int j1 = 0;j1<t.length;j1++)
            {
               // System.out.printf("%.1f ,",t[j1]);
            }
            System.out.printf("-->");
            for(int j1 = 0;j1<ilosc_kategorii;j1++)
            {
                System.out.printf("%.1f ,",siec.run(t)[j1]);
            }
            System.out.println();
        }


    }
}