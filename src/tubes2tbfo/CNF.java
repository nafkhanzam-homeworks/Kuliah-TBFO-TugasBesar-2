package tubes2tbfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * CNF
 */
public class CNF extends CFG {
    private CNF(List<Rule> list, Variable startVariable) {
        super(list, startVariable);
    }

    public static CNF toCNF(CFG cfg) {
        /**
         * Mengubah CFG menjadi CNF
         */
        step1_deleteEpsilonProductions(cfg);
        step2_deleteUnitProductions(cfg);
        step3_deleteUselessVariables(cfg);
        step4_changeForm(cfg);
        return new CNF(cfg.rules, cfg.startVariable);
    }

    private static void step1_deleteEpsilonProductions(CFG cfg) {
        /**
         * Menghilangkan semua produksi ε. Variabel yang dapat menghasilkan ε ditentukan
         * terlebih dahulu, lalu semua produksi ditelusuri dan untuk setiap produksi
         * semua subset yang berpeluang menjadi variabel kosong dihilangkan.
         */
        boolean foundEpsilon = false;
        do {
            foundEpsilon = false;
            for (Rule rule : cfg.rules) {
                Iterator<Product> iter = rule.production.list.iterator();
                while (iter.hasNext()) {
                    Product product = iter.next();
                    if (product.list.size() == 0 && product.list.get(0).equals(Symbol.EPSILON)) {
                        foundEpsilon = true;
                        iter.remove();
                        for (Rule rule2 : cfg.rules) {
                            List<Product> toAdd = new ArrayList<>();
                            for (Product product2 : rule2.production.list) {
                                if (product2.list.contains(rule.variable)) {
                                    toAdd.addAll(_permutate(product2, rule.variable));
                                }
                            }
                            for (Product add : toAdd) {
                                rule2.production.addProduct(add);
                            }
                        }
                    }
                }
            }
        } while (foundEpsilon);
    }

    private static List<Product> _permutate(Product product, Variable variable) {
        List<Product> res = new ArrayList<>();
        int n = 0;
        for (Symbol sym : product.list) {
            if (sym.equals(variable)) {
                ++n;
            }
        }
        for (int i = 1; i < (1 << n); ++i) {
            char[] comb = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0').toCharArray();
            Product newProd = new Product();
            int j = 0;
            for (Symbol sym : product.list) {
                Symbol newSym = new Symbol(sym.value);
                if (sym.equals(variable)) {
                    if (comb[j] == '0') {
                        newProd.list.add(newSym);
                    }
                    ++j;
                } else {
                    newProd.list.add(newSym);
                }
            }
            res.add(newProd);
        }
        return res;
    }

    private static void step2_deleteUnitProductions(CFG cfg) {
        /**
         * Menghilangkan variabel unit produksi. Semua produksi dimana right hand side adalah
         * salah satu dari variabelnya dihilangkan
         */
        for (Rule rule : cfg.rules) {
            Iterator<Product> iter = rule.production.list.iterator();
            List<Product> toAdd = new ArrayList<>();
            while (iter.hasNext()) {
                Product prod = iter.next();
                if (prod.list.size() == 1 && prod.list.get(0) instanceof Variable) {
                    iter.remove();
                    Variable var = (Variable)prod.list.get(0);
                    Production production = cfg.getProduction(var);
                    if (production == null) {
                        throw new RuntimeException("Variable " + var + " has no rule!");
                    }
                    for (Product prodMove : production.list) {
                        toAdd.add(prodMove);
                    }
                }
            }
            rule.production.list.addAll(toAdd);
        }
    }

    private static void step3_deleteUselessVariables(CFG cfg) {
        /**
         * Mempersingkat produksi yang panjang.
         * Variabel baru dapat ditambahkan untuk mempersingkat hasil produksi yang terlalu panjang.
         */
        HashSet<Symbol> set = new HashSet<>();
        for (Rule rule : cfg.rules) {
            Iterator<Product> iter = rule.production.list.iterator();
            while (iter.hasNext()) {
                Product prod = iter.next();
                set.addAll(prod.list);
            }
        }
        Iterator<Rule> iter = cfg.rules.iterator();
        while (iter.hasNext()) {
            Rule rule = iter.next();
            if (!set.contains(rule.variable) && !rule.variable.equals(cfg.startVariable)) {
                iter.remove();
            }
        }
    }

    private static void step4_changeForm(CFG cfg) {
        /**
         * Memindahkan semua terminal ke produksi dimana right hand side adalah satu terminal.
         * Untuk setiap terminal di kanan dari suatu produksi non-unit, variabel pengganti
         * ditambahkan. Dan apabila ada produksi yang merupakan variabel lebih dari dua buah,
         * maka harus diganti dengan variabel baru yang hasil akhirnya harus maksimal dua buah
         * variabel untuk tiap right hand side.
         */
        HashMap<Product, Rule> map = new HashMap<>();
        for (int j = 0; j < cfg.rules.size(); ++j) {
            Rule rule = cfg.rules.get(j);
            Iterator<Product> iter = rule.production.list.iterator();
            List<Product> toAdd = new ArrayList<>();
            while (iter.hasNext()) {
                Product prod = iter.next();
                if (prod.list.size() > 2) {
                    iter.remove();
                    Product newProduct = prod.subProduct(1);
                    Product toReplace = new Product();
                    toReplace.list.add(prod.list.get(0));
                    if (map.containsKey(newProduct)) {
                        toReplace.list.add(map.get(newProduct).variable);
                    } else {
                        Variable var = new Variable(UUID.randomUUID().toString());
                        Production newProduction = new Production();
                        newProduction.addProduct(newProduct);
                        Rule newRule = new Rule(var, newProduction);
                        cfg.rules.add(newRule);
                        map.put(newProduct, newRule);
                        toReplace.list.add(var);
                    }
                    toAdd.add(toReplace);
                    --j;
                } else if (prod.list.size() == 2) {
                    for (int i = 0; i < prod.list.size(); ++i) {
                        Symbol sym = prod.list.get(i);
                        if (sym instanceof Variable) {
                            continue;
                        }
                        Terminal t = (Terminal)sym;
                        Variable toReplace = null;
                        Product check = new Product();
                        check.list.add(t);
                        if (map.containsKey(check)) {
                            toReplace = map.get(check).variable;
                        } else {
                            Variable var = cfg.getResultingSingle(t);
                            if (var == null) {
                                var = new Variable(UUID.randomUUID().toString());
                                Production newProduction = new Production();
                                Product newProduct = new Product();
                                newProduct.list.add(t);
                                newProduction.addProduct(newProduct);
                                Rule newRule = new Rule(var, newProduction);
                                cfg.rules.add(newRule);
                                map.put(newProduct, newRule);
                            }
                            if (!rule.variable.equals(var)) {
                                toReplace = var;
                            }
                        }
                        if (toReplace != null) {
                            prod.list.set(i, toReplace);
                        }
                    }
                }
            }
            rule.production.list.addAll(toAdd);
        }
    }
}