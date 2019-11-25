package tubes2tbfo;

import java.util.List;

/**
 * CNF
 */
public class CNF extends CFG {
    private CNF(List<Rule> list) {
        super(list);
    }

    public static CNF toCNF(CFG cfg) {
        step1_deleteEpsilonProductions(cfg);
        step2_deleteUnitProductions(cfg);
        step3_deleteUselessVariables(cfg);
        step4_changeForm(cfg);
        return new CNF(cfg.rules);
    }

    private static void step1_deleteEpsilonProductions(CFG cfg) {
        
    }

    private static void step2_deleteUnitProductions(CFG cfg) {

    }

    private static void step3_deleteUselessVariables(CFG cfg) {

    }

    private static void step4_changeForm(CFG cfg) {

    }
}