package expression;

import java.util.Map;

public class Const implements Expression {

    private int value;

    public Const(int value){
        this.value = value;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }
}
