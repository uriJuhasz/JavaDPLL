import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uri on 06/03/2017.
 */
public class DIMacsLoader {
    /*
     * Load a DIMacs file to a CNF structure
     */
    public static class SyntaxError extends IOException{
        public SyntaxError(String s) {
            super(s);
        }
    }
    public CNF load(String fileName) throws IOException {
        assert (fileName != null);

        System.out.println("S0");
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
//        Scanner scanner = new Scanner(f);

//        final String commentRE = "^c\\s(.*)$";
        final String commentRE = "^c\\s*(.*)$";
        final Pattern commentPat = Pattern.compile(commentRE);
        String line = "";
        while (br.ready()) {
            line = br.readLine();
            final Matcher m = commentPat.matcher(line);
            if (!m.matches())
                break;
            final String comment = m.group(1);
            if (comment.length() > 0)
                System.out.println("DIMacs Comment: " + comment);
        }

        //Read header
        int numVars;
        int numClauses;
        {
            if (!br.ready())
                throw new SyntaxError("Could not find header line");
            System.out.println("Header: " + line);
            final String headerRE = "p\\s+cnf\\s+(\\d+)\\s+(\\d+)\\s*$";
            final Pattern headerPat = Pattern.compile(headerRE);
            final Matcher hm = headerPat.matcher(line);
            if (!hm.matches())
                throw new SyntaxError("Header line has illegal syntax");
            numVars = Integer.parseInt(hm.group(1));
            numClauses = Integer.parseInt(hm.group(2));
            System.out.println("Atoms:   " + numVars);
            System.out.println("Clauses: " + numClauses);
        }

        CNF r = new CNF(numVars);
        //Read clauses
        {
            int c = 0;
            while (br.ready()) {
                LinkedList<Integer> l = new LinkedList<>();
                line = br.readLine();
                Scanner s = new Scanner(line);
                while (s.hasNextInt())
                {
                    int i = s.nextInt();
                    if (i==0)
                        break;
                    l.add(i);
                }
                r.addClause(new CNF.Clause(l));
                c++;
            }
            if (c!=numClauses)
                ErrorLog.reportError("number of actual clauses " + c + " different from declared " + numClauses);
            System.out.println("Read " + r.clauses.size() + " clauses");
        }
        return r;
    }
}
