package expression;

import exception.DivisionByZeroException;

public interface Expression {
    int evaluate() throws DivisionByZeroException;
    void generate(StringBuilder sb);
}
