package expression;

import exception.runTimeException.RunTimeException;
import expression.function.Function;

import java.util.Map;

public interface Expression {
    int evaluate(Map<String, Function> functions, Map<String, Integer> variables) throws RunTimeException;
    void generate(StringBuilder sb);
    int getLine();
}
