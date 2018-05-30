package antlr4.com.cloudrain.derecho.parser;

import com.cloudrain.derecho.sql.parser.generated.DqlParser;
import com.cloudrain.derecho.sql.parser.generated.DqlParserBaseVisitor;
import com.cloudrain.derecho.sql.parser.generated.DslBaseVisitor;
import com.cloudrain.derecho.sql.parser.generated.DslParser;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created by lugan on 8/29/2016.
 */
public class DslVisitor extends DslBaseVisitor<String>{

    @Override
    public String visitDataSourceExpression(@NotNull DslParser.DataSourceExpressionContext ctx) {
        return ctx.getText();
        //return super.visitDataSourceExpression(ctx);
    }
}
