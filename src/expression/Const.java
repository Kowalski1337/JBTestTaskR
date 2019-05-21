package expression;

import exception.DivisionByZeroException;

public class Const implements Expression {

    private int value;

    public Const(int value){
        this.value = value;
    }

    @Override
    public int evaluate() throws DivisionByZeroException {
        return value;
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append(value);
    }
}
