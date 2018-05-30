package antlr4.com.cloudrain.derecho.parser;

import com.cloudrain.derecho.sql.parser.generated.DqlLexer;
import com.cloudrain.derecho.sql.parser.generated.DqlParser;
import com.cloudrain.derecho.sql.parser.generated.DslLexer;
import com.cloudrain.derecho.sql.parser.generated.DslParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Created by lugan on 11/25/2015.
 */
public class DqlParserService {
    private boolean isTrace = false;

    private DqlParser getDqlParser(String sql) {
        /*ANTLRInputStream input = new ANTLRInputStream(sql);
        DqlLexer lexer = new DqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);


        DqlParser parser = new DqlParser(tokens);
        if (isTrace) {
            parser.setBuildParseTree(true);
            parser.setTrace(true);
        }
        ParseTree parseTree = parser.dql();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new DqlListener(), parseTree);

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new BaseErrorListener());
        parser.setErrorHandler(new BailErrorStrategy());
        return parser;*/
        return null;
    }

    public static void main(String []argv) {
        ANTLRInputStream input = new ANTLRInputStream("entity.Subscriber.CostCenter.Id");
        DslLexer lexer = new DslLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);


        DslParser parser = new DslParser(tokens);
        /*if (isTrace) {
            parser.setBuildParseTree(true);
            parser.setTrace(true);
        }*/
        ParseTree parseTree = parser.expression();
        /*ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new DqlListener(), parseTree);*/

        DslVisitor visitor = new DslVisitor();
        String result = visitor.visit(parseTree);
        System.out.println(result);

        /*parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new BaseErrorListener());
        parser.setErrorHandler(new BailErrorStrategy());
        return parser;*/
    }
}
