package tubes2tbfo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    /**
     * Fungsi akan membaca grammar CFG lalu mengkonversikan menjadi CNF. Setelah menjadi CNF,
     * fungsi akan mengaplikasikan algoritma CYK. Jika file berhasil dicompile, maka akan
     * mengeluarkan tulisan "SUCCEED" warna hijau, jika tidak, akan mengeluarkan tulisan "FAILED"
     * berwarna merah.
     */
    
    private static final String RED = "\033[31m";
    private static final String GREEN = "\033[32m";
    private static final String YELLOW = "\033[33m";
    private static final String NORMAL = "\033[0m";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String cfgFileName = args.length > 0 ? args[0] : null;
        if (cfgFileName == null) {
            System.out.print("Input CFG name: ");
            cfgFileName = scan.nextLine();
        }
        try {
            CFG cfg = CFG.readFile(new File("data/" + cfgFileName + ".cfg"));
            CNF cnf = CNF.toCNF(cfg);
            Files.writeString(new File("data/" + cfgFileName + ".cnf").toPath(), cnf.toString(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            CYK cyk = new CYK(cnf);
            testFiles(cyk, cfgFileName, args.length > 1);
        } catch (NoSuchFileException e) {
            System.err.println("File/folder not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        scan.close();
    }

    public static void testFiles(CYK cyk, String cfgFileName, boolean smallTest) throws Exception {
        System.out.println(smallTest);
        File[] files = new File("data/" + cfgFileName).listFiles();
        cfgFileName += ".cfg";
        System.out.println(YELLOW + "================ TEST FILES ================" + NORMAL);
        if (smallTest) {
            // System.out.print(cyk.cnf);
        }
        System.out.println(YELLOW + "Context Free Grammar: " + NORMAL + cfgFileName);
        int i = 0, succeed = 0;
        for (File file : files) {
            String str = Files.readString(file.toPath());
            boolean res = cyk.test(str);
            System.out.println((res ? GREEN : RED) + ++i + ". " + (res ? "SUCCEED" : "FAILED") + " - " + file.getName() + NORMAL);
            if (res) {
                ++succeed;
            }
            if (smallTest) {
                cyk.printGram();
                System.out.println(String.join(",", str.split("")));
            }
        }
        System.out.println(YELLOW + "============================================" + NORMAL);
        System.out.println(YELLOW + "SUCCEED: " + GREEN + succeed + NORMAL);
        System.out.println(YELLOW + "FAILED : " + RED + (i - succeed) + NORMAL);
    }
}