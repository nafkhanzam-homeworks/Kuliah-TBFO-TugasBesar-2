package tubes2tbfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Product
 */
public class Product {
    public static final String SPACE_CODE = "<&sp>";
    public List<Symbol> list = new ArrayList<>();
    public Product() {
        super();
    }

    public static Product stringToProduct(String str) {
        Product res = new Product();
        String[] strs = str.split(" ");
        Symbol symbol;
        for (String s : strs) {
            if (s.equals("\"") || s.isBlank()) {
                continue;
            }
            if (s.startsWith("\"") && s.endsWith("\"")) {
                symbol = new Terminal(s.substring(1, s.length()-1).replace(SPACE_CODE, " "));
            } else {
                symbol = new Variable(s);
            }
            res.list.add(symbol);
        }
        return res;
    }
}