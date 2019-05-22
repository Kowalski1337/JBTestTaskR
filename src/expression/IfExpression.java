package expression;

import exception.runTimeException.RunTimeException;
import expression.function.Function;

import java.util.Map;

public class IfExpression implements Expression {
    private Expression iff, first, second;
    private int line;

    public IfExpression(Expression iff, Expression first, Expression second, int line){
        this.first = first;
        this.second = second;
        this.iff = iff;
        this.line = line;
    }


    @Override
    public int evaluate(Map<String, Function> functions, Map<String, Integer> variables) throws RunTimeException {
        return iff.evaluate(functions, variables) == 1 ? first.evaluate(functions, variables) : second.evaluate(functions, variables);
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append("[");
        iff.generate(sb);
        sb.append("]?(");
        first.generate(sb);
        sb.append("):(");
        second.generate(sb);
        sb.append(")");
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }
}
