package expression;

import exception.runTimeException.DivisionByZeroException;

public class Equality extends BinaryOperation implements Expression{
    public Equality(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    int doEval(int a, int b) throws DivisionByZeroException {
        return a == b ? 1 : 0;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" = ");
    }
}
