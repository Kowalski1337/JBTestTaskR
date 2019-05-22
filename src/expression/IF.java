package expression;

import exception.DivisionByZeroException;

public class IF implements Expression {
    private Expression iff, first, second;

    public IF(Expression iff, Expression first, Expression second){
        this.first = first;
        this.second = second;
        this.iff = iff;
    }

    @Override
    public int evaluate() throws DivisionByZeroException {
        return iff.evaluate() == 1 ? first.evaluate() : second.evaluate();
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
}
