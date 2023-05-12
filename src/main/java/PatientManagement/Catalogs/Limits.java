package PatientManagement.Catalogs;

public class Limits {
    int upper;
    int lower;

    public Limits(int l, int u) {
        lower = l;
        upper = u;
    }

    public Boolean isWithinLimits(int value) {
        if ((value >= upper) || (value <= lower))
            return false;
        return true;
    }
}
