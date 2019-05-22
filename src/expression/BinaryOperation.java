package expression;

import exception.runTimeException.DivisionByZeroException;
import exception.runTimeException.RunTimeException;
import expression.function.Function;

import java.util.Map;

public abstract class BinaryOperation implements Expression {
    private Expression left;
    private Expression right;
    private int line;

    BinaryOperation(Expression left, Expression right, int line) {
        this.left = left;
        this.right = right;
        this.line = line;
    }

    abstract int doEval(int a, int b) throws DivisionByZeroException;

    @Override
    public int evaluate(Map<String, Function> functions, Map<String, Integer> variables) throws RunTimeException {
        return doEval(left.evaluate(functions, variables), right.evaluate(functions, variables));
    }

    abstract void doGen(StringBuilder sb);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append('(');
        left.generate(sb);
        doGen(sb);
        right.generate(sb);
        sb.append(')');
    }

    @Override
    public int getLine() {
        return line;
    }
}
