package expression;

public class LessThan extends BinaryOperation implements Expression {
    public LessThan(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    int doEval(int a, int b) {
        return a < b ? 1 : 0;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" < ");
    }
}
