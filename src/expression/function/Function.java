package expression.function;

import exception.runTimeException.ArgumentNumberMismatchException;
import exception.runTimeException.RunTimeException;
import expression.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function {
    private Expression functionBody;
    private String name;

    private List<String> params;

    public Function(String name, List<String> params, Expression functionBody) {
        this.params = params;
        this.name = name;
        this.functionBody = functionBody;
    }

    public int apply(List<Integer> args, Map<String, Function> functions) throws RunTimeException {
        if (args.size() != params.size()) {
            throw new ArgumentNumberMismatchException("ARGUMENT NUMBER MISMATCH" + name);
        }

        Map<String, Integer> variables = new HashMap<>();
        for (int i = 0; i < params.size(); ++i) {
            variables.put(params.get(i), args.get(i));
        }

        return functionBody.evaluate(functions, variables);
    }
}
