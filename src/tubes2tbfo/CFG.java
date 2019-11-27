package tubes2tbfo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CFG
 */
public class CFG {
    
    public List<Rule> rules;
    public Variable startVariable;
    public CFG(List<Rule> rules, Variable startVariable) {
        this.rules = rules;
        this.startVariable = startVariable;
    }

    public static CFG readFile(File file) throws Exception {
        /**
         * Membaca file grammar dari python.cfg lalu menjadikannya sebagai Rules dan mendeteksi
         * error dan menyebutkan di line ke berapa jika ada.
         */
        List<String> lines = Files.readAllLines(file.toPath());
        List<Rule> list = new ArrayList<>();
        int lineInfo = 1;
        Rule rule;
        for (String line : lines) {
            try {
                rule = Rule.stringToRule(line);
                if (rule != null) {
                    list.add(rule);
                }
            } catch (Exception e) {
                System.err.println("Error on file " + file.getName() + " (line: " + lineInfo + ")");
                throw e;
            }
            ++lineInfo;
        }
        return new CFG(list, list.isEmpty() ? null : list.get(0).variable);
    }

    private Map<Variable, Production> dpProd = new HashMap<>();
    public Production getProduction(Variable var) {
        /**
         * Mengembalikan produk dari sebuah grammar CFG
         */
        if (dpProd.containsKey(var)) {
            return dpProd.get(var);
        }
        for (Rule rule : rules) {
            if (rule.variable.equals(var)) {
                dpProd.put(var, rule.production);
                return rule.production;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        /**
         * Mengubah Rules menjadi string
         */
        StringBuilder sb = new StringBuilder();
        for (Rule rule : rules) {
            sb.append(rule.toString()).append("\n");
        }
        return sb.toString();
    }

    private Map<Pair<Variable, Variable>, List<Variable>> dp = new HashMap<>();
    public List<Variable> getResulting(Variable a, Variable b) {
        Pair<Variable, Variable> pair = new Pair<>(a, b);
        if (dp.containsKey(pair)) { // Dynamic Programming...
            return dp.get(pair);
        }
        List<Variable> res = new ArrayList<>();
        Product product = new Product();
        product.list.add(a);
        product.list.add(b);
        for (Rule rule : rules) {
            if (rule.production.containsProduct(product)) {
                res.add(rule.variable);
            }
        }
        dp.put(pair, res);
        return res;
    }

    private Map<Terminal, List<Variable>> dpTerminal = new HashMap<>();
    public List<Variable> getResulting(Terminal t) {
        /**
         * Mencari dan mengembalikan Rules yang memiliki product yang dicari
         */
        if (dpTerminal.containsKey(t)) { // Dynamic Programming...
            return dpTerminal.get(t);
        }
        List<Variable> res = new ArrayList<>();
        Product product = new Product();
        product.list.add(t);
        for (Rule rule : rules) {
            if (rule.production.containsProduct(product)) {
                res.add(rule.variable);
            }
        }
        dpTerminal.put(t, res);
        return res;
    }

    private Map<Terminal, Variable> dpTerminalSingle = new HashMap<>();
    public Variable getResultingSingle(Terminal t) {
        /**
         * Mencari dan mengembalikan Rules yang memiliki product yang dicari
         */
        if (dpTerminalSingle.containsKey(t)) { // Dynamic Programming...
            return dpTerminalSingle.get(t);
        }
        Product product = new Product();
        product.list.add(t);
        for (Rule rule : rules) {
            if (rule.production.containsProduct(product) && rule.production.list.size() == 1) {
                dpTerminalSingle.put(t, rule.variable);
                return rule.variable;
            }
        }
        return null;
    }
}