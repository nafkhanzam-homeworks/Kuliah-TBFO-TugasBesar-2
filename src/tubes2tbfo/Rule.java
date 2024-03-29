package tubes2tbfo;

/**
 * Grammar
 */
public class Rule {
    public Variable variable;
    public Production production;

    public Rule(Variable variable, Production production) {
        this.variable = variable;
        this.production = production;
    }

    public static Rule stringToRule(String line) throws Exception {
        line = line.trim();
        if (line.isBlank() || line.startsWith("#")) {
            return null;
        }
        String[] strs = line.split(" -> ");
        if (strs.length != 2) {
            throw new Exception("Arrow is not valid!");
        }
        return new Rule(new Variable(strs[0]), Production.stringToProduction(strs[1]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(variable.toString() + " -> ");
        for (Product prod : production.list) {
            sb.append(prod.toString() + " | ");
        }
        return sb.toString().substring(0, sb.length() - 3);
    }
}