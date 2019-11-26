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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (list == null) {
            if (other.list != null)
                return false;
        } else if (!list.equals(other.list))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol sym : list) {
            sb.append(sym.toString()).append(" ");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public Product subProduct(int startIndex) {
        Product res = new Product();
        res.list.addAll(list.subList(startIndex, list.size()));
        return res;
    }
}