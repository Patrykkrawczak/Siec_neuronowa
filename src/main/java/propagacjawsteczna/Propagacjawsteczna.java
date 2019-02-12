package propagacjawsteczna;

public class Propagacjawsteczna {

    private warstwa[] warstwy;

    public Propagacjawsteczna(int wielkoscwejscia, int ukryteilosc, int wyjscie) {
        warstwy = new warstwa[2];
        warstwy[0] = new warstwa(wielkoscwejscia, ukryteilosc);
        warstwy[1] = new warstwa(ukryteilosc, wyjscie);
    }

    public warstwa getwarstwa(int index) {
        return warstwy[index];
    }

    public float[] run(float[] dane) {
        float[] aktywacja = dane;
        for (int i = 0; i < warstwy.length; i++) {
            aktywacja = warstwy[i].run(aktywacja);
        }
        return aktywacja;
    }

    public void ucz(float[] wejscie, float[] oczekiwane, float LR, float momentum) {

        float[] aktualne = run(wejscie);
        float[] error = new float[aktualne.length];

        for (int i = 0; i < error.length; i++) {
            error[i] = oczekiwane[i] - aktualne[i];
        }

        for (int i = warstwy.length - 1; i >= 0; i--) {
            error = warstwy[i].ucz(error, LR, momentum);
        }
    }
}
