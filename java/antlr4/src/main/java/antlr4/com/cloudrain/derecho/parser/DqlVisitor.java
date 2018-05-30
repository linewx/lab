package antlr4.com.cloudrain.derecho.parser;

import com.cloudrain.derecho.sql.parser.generated.DqlParser;
import com.cloudrain.derecho.sql.parser.generated.DqlParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by lugan on 8/29/2016.
 */
public class DqlVisitor extends DqlParserBaseVisitor{
    @Override
    public Object visitWhereClause(@NotNull DqlParser.WhereClauseContext ctx) {
        System.out.println(ctx.getText());
        return super.visitWhereClause(ctx);
    }

    @Override
    public Object visitSelect(@NotNull DqlParser.SelectContext ctx) {
        System.out.println(ctx.getText());
        return ctx.getText();
        //return super.visitSelect(ctx);
    }

    @Override
    public Object visitDqlExpression(@NotNull DqlParser.DqlExpressionContext ctx) {
        return super.visitDqlExpression(ctx);
    }

    @Override
    public Object visitDql(@NotNull DqlParser.DqlContext ctx)
    {
        return this.visit(ctx.select());
    }




    /*@Override
    public Object visitTerminal(@NotNull TerminalNode node) {
        return "";
        //return super.visitTerminal(node);
    }*/
}
