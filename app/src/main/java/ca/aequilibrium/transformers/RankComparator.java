package ca.aequilibrium.transformers;


import java.util.Comparator;

public class RankComparator implements Comparator<Transformer> {
    public int compare(Transformer f1, Transformer f2) {
        if (f1.getRank() < f2.getRank()) {
            return 1;
        } else if (f1.getRank() > f2.getRank()) {
            return -1;
        }
        return 0;
    }
}
