package tubes2tbfo;

import java.util.*;
import java.io.*;

public class CFG {
    public HashMap<Variable, List<Product>> rules = new HashMap<>();
    public List<Variable> variables = new ArrayList<>();

    public static CFG createCFG(File file) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            FileReader r = new FileReader(file);
            int c = -1;
            while ((c = r.read()) != -1) {
                sb.append(c);
            }
            r.close();
            return createCFG(sb.toString().split("\n"));
        } catch (Exception e) {
            throw e;
        }
    }

    public static CFG createCFG(String[] content) {
        CFG res = new CFG();
        for (int line = 1; line <= content.length; ++line) {
            String s = content[line-1];
            s = s.trim();
            if (s.isBlank() || s.startsWith("#")) {
                continue;
            }
            String[] strs = s.replaceAll(" +", " ").split(" ");
            if (strs.length < 3 || !strs[1].equals("->")) {
                throw new RuntimeException("Syntax is not correct :( ! (line: " + line + ")");
            }
            List<Product> list = new ArrayList<>();
            Product prod = new Product();
            for (int i = 2; i < strs.length; ++i) {
                if (strs[i].equals("|")) {
                    list.add(prod);
                    prod = new Product();
                } else {
                    prod.products.add(strs[i]);
                }
            }
            var v = new Variable(strs[0]);
            res.rules.put(v, list);
            res.variables.add(v);
        }
        return res;
    }
}