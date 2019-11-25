package tubes2tbfo;

import java.io.File;
import java.nio.file.Files;

/**
 * Main
 */
public class Main {
    private static final String RED = "\033[31m";
    private static final String GREEN = "\033[32m";
    private static final String YELLOW = "\033[33m";
    private static final String NORMAL = "\033[0m";
    public static void main(String[] args) throws Exception {
        String cfgFileName = "test.cfg";
        CYK cyk = new CYK(CNF.toCNF(CFG.readFile(new File(cfgFileName))));
        testFiles(cyk, cfgFileName);
    }

    public static void testFiles(CYK cyk, String cfgFileName) throws Exception {
        File[] files = new File("/test/").listFiles();
        System.out.println(YELLOW + "================ TEST FILES ================" + NORMAL);
        System.out.println(YELLOW + "Context Free Grammar: " + NORMAL + cfgFileName);
        int i = 0, succeed = 0;
        for (File file : files) {
            boolean res = cyk.test(Files.readString(file.toPath()));
            System.out.println((res ? GREEN : RED) + (++i) + ". " + (res ? "SUCCEED" : "FAILED") + " - " + file.getName() + NORMAL);
            if (res) {
                ++succeed;
            }
        }
        System.out.println(YELLOW + "============================================" + NORMAL);
        System.out.println(YELLOW + "SUCCEED: " + GREEN + succeed + NORMAL);
        System.out.println(YELLOW + "FAILED : " + RED + (i - succeed) + NORMAL);
    }
}