package expression;

import exception.DivisionByZeroException;

public class Mod extends BinaryOperation implements  Expression {
    public Mod(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    int doEval(int a, int b) throws DivisionByZeroException {
        if (b == 0) {
            StringBuilder sb = new StringBuilder();
            generate(sb);
            throw new DivisionByZeroException("RUNTIME ERROR ".concat(sb.toString()));
        }
        return a % b;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" % ");
    }
}
