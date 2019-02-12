package propagacjawsteczna;



public class funkcjaaktywacji {

    public static float sigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    public static float PochSigmoid(float x) {
        return x*(1-x);
    }
}