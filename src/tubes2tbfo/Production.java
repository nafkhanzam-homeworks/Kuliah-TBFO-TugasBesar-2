package tubes2tbfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Production
 */
public class Production {
    public List<Product> list = new ArrayList<>();
    public Production() {
        super();
    }

    public static Production stringToProduction(String _str) {
        String str = "";
        boolean inside = false;
        for (char c : _str.toCharArray()) {
            if (c == '"') {
                inside = !inside;
            }
            if (inside && c == ' ') {
                str += Product.SPACE_CODE;
            } else {
                str += c;
            }
        }
        String[] strs = str.split(" \\| ");
        Production res = new Production();
        for (String s : strs) {
            res.addProduct(Product.stringToProduct(s));
        }
        return res;
    }
    public void addProduct(Product product) {
        if (!containsProduct(product)) {
            list.add(product);
        }
    }
    public boolean containsProduct(Product product) {
        return list.contains(product);
    }
}