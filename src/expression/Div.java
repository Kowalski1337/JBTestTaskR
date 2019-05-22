package expression;

import exception.runTimeException.DivisionByZeroException;

public class Div extends BinaryOperation implements Expression {
    public Div(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    int doEval(int a, int b) throws DivisionByZeroException {
        if (b == 0) {
            StringBuilder sb = new StringBuilder();
            generate(sb);
            throw new DivisionByZeroException("RUNTIME ERROR ".concat(sb.toString()).concat(":").concat(Integer.toString(getLine())));
        }
        return a / b;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" / ");
    }
}
