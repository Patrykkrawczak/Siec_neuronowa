package Perceptron;

public class Perceptron {

    private float[] wagi;
    private float[][] wejscie;
    private float[] wyjscie;
    private int numOfwagi;

    public Perceptron(float[][] wejscie, float[] wyjscie) {
        this.wejscie = wejscie;
        this.wyjscie = wyjscie;
        this.numOfwagi = wejscie[0].length;
        this.wagi = new float[numOfwagi];

        inicjalizacja();
    }

    private void inicjalizacja() {
        for(int i=0;i<numOfwagi;++i)
            wagi[i] = 0;
    }

    public void ucz(float LR) {

        float blad = 1;

        while ( blad != 0 ) {

            blad = 0;

            for(int i=0;i<wyjscie.length;i++) {

                float obliczdwyjscie = obliczwyjscie(wejscie[i]);
                float b1 = wyjscie[i]-obliczdwyjscie;

                blad += b1;


                for(int j=0;j<numOfwagi;j++) {
                    wagi[j] = wagi[j] + LR * (wejscie[i][j] * b1);

                    System.out.println("nowe wagi: " + wagi[j]);
                }
            }

            System.out.println("strata wynosi: " + blad);
        }

    }

    public float obliczwyjscie(float[] wejscie) {

        float sum = 0f;

        for(int i=0;i<wejscie.length;++i)
            sum = sum + wagi[i] * wejscie[i];

        return funkcjaaktywacji.stepFunction(sum);
    }
}