import java.util.Comparator;

public class CookieSorter implements Comparator<CookieAccount> {
    @Override
    public int compare(CookieAccount o1, CookieAccount o2) {
        return o2.getCookieCount()-o1.getCookieCount();
    }
}
