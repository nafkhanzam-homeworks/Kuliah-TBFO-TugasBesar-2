package tubes2tbfo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
        return new CFG(list, list.size() > 0 ? list.get(0).variable : null);
    }
}