package expression;

public class GreaterThen extends BinaryOperation implements Expression {
    public GreaterThen(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    int doEval(int a, int b) {
        return a > b ? 1 : 0;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" > ");
    }
}
