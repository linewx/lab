import java.util.HashSet;
import java.util.Set;

/**
 * Created by lugan on 11/4/2015.
 */

public class FinalSyntax {
    private final Set<String> foo = new HashSet<String>();

    public FinalSyntax() {
        foo.add("foo1");
        foo.add("foo2");
        foo.add("foo3");
    }

    public void addItem(String item) {
        foo.add(item);
    }

   /* public void clear() {
        foo = new HashSet<String>();
    }*/

    public void printItems() {
        for (String item : foo) {
            System.out.println(item);
        }
    }

    public static void main(String []args) {
       /* FinalSyntax finalSyntax = new FinalSyntax();
        finalSyntax.addItem("foo4");
        finalSyntax.printItems();*/
        String a = new String("String");
        String b = new String("String");
        if(a == "String") {
            System.out.println("true");
        }else {
            System.out.println("false");
        }

    }
}
