package tubes2tbfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CNF
 */
public class CNF extends CFG {
    private CNF(List<Rule> list, Variable startVariable) {
        super(list, startVariable);
    }

    public static CNF toCNF(CFG cfg) {
        step1_deleteEpsilonProductions(cfg);
        step2_deleteUnitProductions(cfg);
        step3_deleteUselessVariables(cfg);
        step4_changeForm(cfg);
        return new CNF(cfg.rules, cfg.startVariable);
    }

    private static void step1_deleteEpsilonProductions(CFG cfg) {
        boolean foundEpsilon = false;
        do {
            foundEpsilon = false;
            for (Rule rule : cfg.rules) {
                for (int index = 0; index < rule.production.list.size(); ++index) {
                    Product product = rule.production.list.get(index);
                    if (product.list.contains(Symbol.EPSILON)) {
                        foundEpsilon = true;
                        rule.production.list.remove(index);
                        // TODO: ADD THE COMBINATION OF DELETED PRODUCT TO PRODUCTION
                    }
                }
            }
        } while (foundEpsilon);
        // TODO: ADD GET_PRODUCTION BY VARIABLE IN PRODUCTION.JAVA, AND THEN THE CHECKING WETHER THAT PRODUCTION WILL PRODUCT EPSILON OR NOT CAN BE DONE RECURSIVELY!
        
    }

    private static void step2_deleteUnitProductions(CFG cfg) {
        // TODO: IMPLEMENT
    }

    private static void step3_deleteUselessVariables(CFG cfg) {
        // TODO: IMPLEMENT
    }

    private static void step4_changeForm(CFG cfg) {
        // TODO: IMPLEMENT
    }

    private Map<Pair<Variable, Variable>, List<Variable>> dp = new HashMap<>();
    public List<Variable> getResulting(Variable a, Variable b) {
        Pair<Variable, Variable> pair = new Pair<>(a, b);
        if (dp.containsKey(pair)) {
            return dp.get(pair);
        }
        List<Variable> res = new ArrayList<>();
        
        return res;
    }
}