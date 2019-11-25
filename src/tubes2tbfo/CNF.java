package tubes2tbfo;

public class CNF extends CFG {
    private CNF() {}
    public CNF toCNF() {
        if (this instanceof CNF) {
            return this;
        }
        // TODO: implement
        for (int i = 0; i < variables.size(); ++i) {
            Variable v = variables.get(i);
            
        }
        return this;
    }
}