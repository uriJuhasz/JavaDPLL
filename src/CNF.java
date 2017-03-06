import java.util.ArrayList;
import java.util.List;

/*
 * CNF representation of a propositional SAT problem
 * Variables are integers, literals use the integer sign for literal sign
 * e.g. x_5 \/ !x_6 => { 5, -6 }
 */
public class CNF {
    public CNF(int numVars){this.numVars = numVars;}

    public static class Clause{
        public Clause(List<Integer> l){
            assert(l!=null);
            literals = new int[l.size()];
            int c=0;
            for (Integer i : l)
                literals[c++] = i;
        }
        public int[] literals;
    };
    public void addClause(Clause c)
    {
        assert(c!=null);
        clauses.add(c);
    }


    public ArrayList<Clause> clauses = new ArrayList<>();
    private int numVars;
}
