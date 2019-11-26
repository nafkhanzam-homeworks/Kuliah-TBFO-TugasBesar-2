package tubes2tbfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Product
 */
public class Product {
    public static final String SPACE_CODE = "<sp>";
    public List<Symbol> list = new ArrayList<>();
    public Product() {
        super();
    }

    private static String _replaceEncoding(String s) {
        return s.replace(SPACE_CODE, " ");
    }

    public static Product stringToProduct(String str) {
        Product res = new Product();
        String[] strs = str.split(" ");
        for (String s : strs) {
            if (s.isBlank()) {
                continue;
            }
            if (s.startsWith("\"") && s.endsWith("\"")) {
                if (s.equals("\"\"")) {
                    res.list.add(new Terminal((char)0));
                } else {
                    s = _replaceEncoding(s);
                    for (char c : s.substring(1, s.length()-1).toCharArray()) {
                        res.list.add(new Terminal(c));
                    }
                }
            } else {
                res.list.add(new Variable(s));
            }
        }
        return res;
    }
}