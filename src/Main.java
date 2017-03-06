import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public final class Main {
    public static void main(String[] args){
        System.out.println("Start");

        uniqueArguments(args);

        try{
            String fileName = getFileName(args);
            System.out.println("Loading file " + fileName);
            CNF cnf = new DIMacsLoader().load(fileName);
        }catch(Exception e){
            String em = e.getMessage();
            if (em==null)
                em = e.getClass().getName();
            ErrorLog.reportError(em);
            System.out.println("Loading file failed: " + em);
        }


        System.out.println("End");
    }

    private static void uniqueArguments(String[] args) {
        HashSet<String> set = new HashSet<>();
        List<String> o = new LinkedList<>();

        System.out.println("  Arguments:");
        int i = 0;
        for (String a : args) {
            System.out.println(i + ":    " + a);
            if (set.add(a))
                o.add(a);
            i++;
        }

        System.out.println("  Unique arguments:");
        for (String a : o)
            System.out.println("    " + a);
    }

    private static String getFileName(String[] args) throws Exception{
        final String errorMessage = "No input file name given on the command line";
        if (args.length==0 || args[0]==null) {
            ErrorLog.reportError(errorMessage);
            throw new Exception("Error: " + errorMessage );
        }else
            return args[0];
    }

}
