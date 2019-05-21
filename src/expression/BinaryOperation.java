package expression;

import exception.DivisionByZeroException;

public abstract class BinaryOperation implements Expression {
    private Expression left;
    private Expression right;

    BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    abstract int doEval(int a, int b) throws DivisionByZeroException;

    @Override
    public int evaluate() throws DivisionByZeroException {
        return doEval(left.evaluate(), right.evaluate());
    }

    abstract void doGen(StringBuilder sb);

    @Override
    public void generate(StringBuilder sb) {
        sb.append('(');
        left.generate(sb);
        doGen(sb);
        right.generate(sb);
        sb.append(')');
    }
}
