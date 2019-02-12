package propagacjawsteczna;

import java.util.Arrays;
import java.util.Random;

public class warstwa {

    private float[] wyjscia;
    private float[] wejscia;
    private float[] wagi;
    private float[] poprz_wagi;
    private Random los;

    public warstwa(int wielkosc_wejscia, int wielkosc_wyjscia) {
        wyjscia = new float[wielkosc_wyjscia];
        wejscia = new float[wielkosc_wejscia + 1];
        wagi = new float[(1 + wielkosc_wejscia) * wielkosc_wyjscia];
        poprz_wagi = new float[wagi.length];
        this.los = new Random();
        iniciuj();
    }

    public void iniciuj() {
        for (int i = 0; i < wagi.length; i++) {
            wagi[i] = (los.nextFloat() - 0.5f) * 4f; //zeby zwracał liczbę w przedziale [-2,2]
        }
    }

    public float[] run(float[] Dane_wejsciowe) {

        System.arraycopy(Dane_wejsciowe, 0, wejscia, 0, Dane_wejsciowe.length);
        wejscia[wejscia.length - 1] = 1; // ostatni zostawić jako bias
        int offset = 0;  //do ogarnięcia tablicy

        //napisac przechodzenie przez warstwę
        for (int i = 0; i < wyjscia.length; i++) {
            for (int j = 0; j < wejscia.length; j++) {
                wyjscia[i] += wagi[offset + j] * wejscia[j];
            }

            wyjscia[i] = funkcjaaktywacji.sigmoid(wyjscia[i]);
            offset += wejscia.length;
        }

        return Arrays.copyOf(wyjscia, wyjscia.length);
    }

    public float[] ucz(float[] strata, float LR, float momentum) {

        int offset = 0;
        float[] update_strat = new float[wejscia.length];

        for (int i = 0; i < wyjscia.length; i++) {

            float delta = strata[i] * funkcjaaktywacji.PochSigmoid(wyjscia[i]);
            for (int j = 0; j < wejscia.length; j++) {
                int wczesniejszy_index = offset + j;
                update_strat[j] = update_strat[j] + wagi[wczesniejszy_index] * delta;
                float pw = wejscia[j] * delta * LR;
                wagi[wczesniejszy_index] += poprz_wagi[wczesniejszy_index] * momentum + pw;
                poprz_wagi[wczesniejszy_index] = pw;
            }

            offset += wejscia.length;
        }

        return update_strat;
    }
}