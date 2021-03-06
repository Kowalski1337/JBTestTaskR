package expression;

import exception.runTimeException.ArgumentNumberMismatchException;
import exception.runTimeException.NoSuchFunctionException;
import exception.runTimeException.RunTimeException;
import expression.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallExpression implements Expression {

    private List<Expression> args;
    private String name;
    private int line;

    public CallExpression(List<Expression> args, String name, int line) {
        this.args = args;
        this.name = name;
        this.line = line;
    }


    @Override
    public int evaluate(Map<String, Function> functions, Map<String, Integer> variables) throws RunTimeException {
        if (!functions.containsKey(name)){
            throw new NoSuchFunctionException("FUNCTION NOT FOUND ".concat(name).concat(":").concat(Integer.toString(getLine())));
        }
        if (args.size() != functions.get(name).numberOFArgs()) {
            throw new ArgumentNumberMismatchException("ARGUMENT NUMBER MISMATCH ".concat(name).concat(":").concat(Integer.toString(getLine())));
        }

        List<Integer> evalArgs = new ArrayList<>();
        for (Expression e : args){
            evalArgs.add(e.evaluate(functions, variables));
        }
        return functions.get(name).apply(evalArgs, functions);
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.append(name);
        sb.append('(');
        sb.append(args.stream().map(Object::toString).collect(Collectors.joining(",")));
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
