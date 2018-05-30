package antlr4.com.cloudrain.derecho.parser;

import com.cloudrain.derecho.sql.parser.generated.DqlParser;
import com.cloudrain.derecho.sql.parser.generated.DqlParserBaseListener;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created by lugan on 8/25/2016.
 */
public class DqlListener extends DqlParserBaseListener{
    @Override
    public void enterSelect(@NotNull DqlParser.SelectContext ctx) {
        //super.enterSelect(ctx);
        System.out.println( "Entering Select : " + ctx.getText() );
    }

    @Override
    public void exitSelect(@NotNull DqlParser.SelectContext ctx) {
        //super.exitSelect(ctx);
        System.out.println( "Existing Select : " + ctx.getText() );
    }
}
