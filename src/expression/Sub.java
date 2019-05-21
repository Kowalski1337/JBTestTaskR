package expression;

public class Sub extends BinaryOperation implements Expression {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    int doEval(int a, int b) {
        return a - b;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" - ");
    }
}
