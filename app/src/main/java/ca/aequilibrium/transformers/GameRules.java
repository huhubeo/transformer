package ca.aequilibrium.transformers;

public class GameRules {
    private static int COURAGE_COMPARE_POINT = 4;
    private static int STRENGTH_COMPARE_POINT = 3;
    private static int SKILL_COMPARE_POINT = 3;
    private static String OPTIMUS = "Optimus Prime";
    private static String PREDAKING = "Predaking";

    /**
     *
     * @param f1
     * @param f2
     * @return -1: f2 win, 0: tie, 1: f1 win
     */
    public static int compareByCourageAndStrength(Transformer f1, Transformer f2) {
        int courageCompare = Math.abs(f1.getCourage() - f2.getCourage());
        int strengthCompare = Math.abs(f1.getStrength() - f2.getStrength());
        if (courageCompare >= COURAGE_COMPARE_POINT && strengthCompare >= STRENGTH_COMPARE_POINT) {
            if (f1.getCourage() > f2.getCourage() && f1.getStrength() > f2.getStrength()) {
                return 1;
            } else if (f1.getCourage() < f2.getCourage() && f1.getStrength() < f2.getStrength()) {
                return -1;
            }
        }
        return 0;
    }

    /**
     *
     * @param f1
     * @param f2
     * @return -1: f2 win, 0: tie, 1: f1 win
     */
    public static int compareBySkill(Transformer f1, Transformer f2) {
        int skillCompare = Math.abs(f1.getSkill() - f2.getSkill());
        if (skillCompare >= SKILL_COMPARE_POINT) {
            if (f1.getSkill() > f2.getSkill()) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    /**
     *
     * @param f1
     * @param f2
     * @return -1: f2 win, 0: tie, 1: f1 win
     */
    public static int compareByOverallRating(Transformer f1, Transformer f2) {
        if (f1.getOverallRating() > f2.getOverallRating()) {
            return 1;
        } else if (f1.getOverallRating() < f2.getOverallRating()) {
            return -1;
        }
        return 0;
    }

    //special rules

    /**
     *
     * @param f1
     * @param f2
     * @return -1: f2 win, 0: tie, 1: f1 win, 2: game end all competitors destroyed
     */
    public static int compareByName(Transformer f1, Transformer f2) {
        if ((isOptimusPrime(f1) && !isOptimusPrime(f2) && !isPredaking(f2)) ||
                (isPredaking(f1) && !isOptimusPrime(f2) && !isPredaking(f2))) {
            return 1;
        } else if ((isOptimusPrime(f2) && !isOptimusPrime(f1) && !isPredaking(f1)) ||
                (isPredaking(f2) && !isOptimusPrime(f1) && !isPredaking(f1))) {
            return -1;
        } else if ( (isOptimusPrime(f1) && (isOptimusPrime(f2) || isPredaking(f2))) ||
                (isOptimusPrime(f2) && (isOptimusPrime(f1) || isPredaking(f1))) ) {
            return 2;
        }

        return 0;
    }

    /**
     *
     * @param f1
     * @param f2
     * @return -1: f2 win, 0: tie, 1: f1 win, 2: game ended
     */
    public static int compareByGameRules(Transformer f1, Transformer f2) {
        int result = 0;

        int nameResult = compareByName(f1, f2);
        switch (nameResult) {
            case -1:
            case 1:
            case 2:
                result = nameResult;
                break;
            case 0:
                //continue to compare
                int skillResult = compareBySkill(f1, f2);
                if (skillResult != 0) {
                    result = skillResult;
                } else {
                    int courageStrength = compareByCourageAndStrength(f1, f2);
                    if (courageStrength != 0) {
                        result = courageStrength;
                    } else {
                        result = compareByOverallRating(f1, f2);
                    }
                }
                break;
        }

        return result;
    }

    private static boolean isOptimusPrime(Transformer f) {
        return OPTIMUS.toLowerCase().equals(f.getName().toLowerCase());
    }

    private static boolean isPredaking(Transformer f) {
        return PREDAKING.toLowerCase().equals(f.getName().toLowerCase());
    }
}
