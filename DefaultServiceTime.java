import java.util.function.Supplier;

class DefaultServiceTime implements Supplier<Double> {
    private final static double[] doubleList = { // for case 10
            1.000, 9.000, 1.000, 1.000, 1.000, 1.000, 1.000, 1.000, 1.000, 1.000, 10.000, 10.000, 1.000

    };
    private int count = -1;

    public Double get() {
        return 1.0;
    }
} 