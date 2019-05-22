package expression;

public class Prod extends BinaryOperation implements Expression {
    public Prod(Expression left, Expression right,int line) {
        super(left, right, line);
    }

    @Override
    int doEval(int a, int b) {
        return a * b;
    }

    @Override
    void doGen(StringBuilder sb) {
        sb.append(" * ");
    }
}
