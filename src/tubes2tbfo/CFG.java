package tubes2tbfo;

import java.util.*;
import java.io.*;

public class CFG {
    public HashMap<Variable, List<Product>> rules = new HashMap<>();

    public static CFG getCFG(File file) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            FileReader r = new FileReader(file);
            int c = -1;
            while ((c = r.read()) != -1) {
                sb.append(c);
            }
            r.close();
            return getCFG(sb.toString().split("\n"));
        } catch (Exception e) {
            throw e;
        }
    }

    public static CFG getCFG(String[] content) {
        for (String s : content) {
            s = s.trim();
            if (s.isBlank() || s.startsWith("//")) {
                continue;
            }
            String[] strs = s.replaceAll(" +", " ").split(" ");
            if (strs.length < 3 || !strs[1].equals("->")) {
                throw new RuntimeException("Syntax is not correct :( !");
            }
            String var = strs[0];
            List<List<String>> cfgs = new ArrayList<>();
            for (int i = 2; i < strs.length; ++i) {
                List<String> list = new ArrayList<>();
                
            }
        }
        return null;
    }
}