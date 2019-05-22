package expression;

import exception.runTimeException.NoSuchParameterException;
import expression.function.Function;

import java.util.Map;

public class Variable implements Expression {

    private String name;
    public Variable(String name){
        this.name = name;
    }

    @Override
    public int evaluate(Map<String, Function> functions, Map<String, Integer> variables) throws NoSuchParameterException {
        if (!variables.containsKey(name)){
            throw new NoSuchParameterException("PARAMETER NOT FOUND" + name);
        }
        return variables.get(name);
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }
}
