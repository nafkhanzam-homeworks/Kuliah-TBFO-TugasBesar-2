package tubes2tbfo;

import java.util.ArrayList;
import java.util.List;

/**
 * CYK
 */
public class CYK {
    public CNF cnf;
    public CYK(CNF cnf) {
        this.cnf = cnf;
    }

    private List<Variable>[][] gram;
    // Assuming terminal only have one variable (as it should be!)
    @SuppressWarnings("unchecked")
    public boolean test(String str) {
        // str = str.replaceAll("\n", " ");
        // TODO: IMPLEMENT CYK
        int n = str.length();
        gram = new List[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                gram[i][j] = new ArrayList<>();
            }
        }
        char[] chars = str.toCharArray();
        for (int i = n-1; i >= 0; --i) {
            for (int j = 0; j <= i; ++j) {
                if (i == n-1) {
                    gram[i][j].addAll(cnf.getResulting(new Terminal(chars[j])));
                } else {
                    List<Variable> comb = gram[i][j];
                    for (int k = 0; k < n-i-1; ++k) {
                        comb(comb, gram[n-k-1][j], gram[i+k+1][j+k+1]);
                    }
                }
            }
        }
        return gram[0][0].contains(cnf.startVariable);
    }

    public void printGram() {
        int i = 0;
        for (List<Variable>[] line : gram) {
            printLineGram(line, i++); System.out.println();
        }
    }

    public void printLineGram(List<Variable>[] line, int j) {
        printList(line[0]);
        for (int i = 1; i <= j; ++i) {
            System.out.print(","); printList(line[i]);
        }
    }

    public void printList(List<Variable> list) {
        System.out.print("[");
        if (!list.isEmpty()) {
            System.out.print(list.get(0));
            for (int i = 1; i < list.size(); ++i) {
                System.out.print("," + list.get(i));
            }
        }
        System.out.print("]");
    }

    public void comb(List<Variable> res, List<Variable> aList, List<Variable> bList) {
        for (int i = 0; i < aList.size(); ++i) {
            for (int j = 0; j < bList.size(); ++j) {
                res.addAll(cnf.getResulting(aList.get(i), bList.get(j)));
            }
        }
    }
}