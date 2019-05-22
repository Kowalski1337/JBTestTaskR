package expression;

import java.util.Map;

public class Const implements Expression {

    private int value;
    private int line;

    public Const(int value, int line){
        this.value = value;
        this.line = line;
    }


    @Override
    public int evaluate(Map<String, expression.function.Function> functions, Map<String, Integer> variables) {
        return value;
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append(value);
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }
}
