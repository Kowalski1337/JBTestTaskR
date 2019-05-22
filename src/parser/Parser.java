package parser;

import exception.ParseException;
import expression.*;
import expression.function.Function;
import javafx.util.Pair;
import lexer.Lexer;
import lexer.Token;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private Lexer lex;
    private int curLine;

    public Parser(BufferedReader br) throws ParseException {
        lex = new Lexer(br);
    }

    public Pair<Map<String, Function>, Expression> parse() throws ParseException {
        curLine = 1;
        lex.nextToken();
        return parseProgram();
    }

    private Pair<Map<String, Function>, Expression> parseProgram() throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case NUMBER:
            case MINUS:
            case LPAR1:
            case LPAR2:
                ans = new Pair<>(new HashMap<>(), parseExpression2());
                break;
            case NAME:
                String name = lex.getName();
                lex.nextToken();
                ans = parseContProgram(name);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }

        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }

        return ans;
    }

    private Pair<Map<String, Function>, Expression> parseContProgram(String name) throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case LPAR1:
                lex.nextToken();
                ans = parseArgsOrParams(new ArrayList<>(), new ArrayList<>(), name);
                break;
            case END:
                ans = new Pair<>(new HashMap<>(), new Variable(name, curLine));
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private Pair<Map<String, Function>, Expression> parseArgsOrParams(List<String> params, List<Expression> args, String name) throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case NUMBER:
            case LPAR1:
            case LPAR2:
            case MINUS:
                Expression expression = parseExpression2();
                args.add(expression);
                parseOtherArgs2(args);
                ans = new Pair<>(new HashMap<>(), new CallExpression(args, name, curLine));
                break;
            case NAME:
                String name1 = lex.getName();
                lex.nextToken();
                ans = parseCallOrOther1(params, args, name, name1);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private Pair<Map<String, Function>, Expression> parseCallOrOther1(List<String> params, List<Expression> args, String name1, String name2) throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case RPAR1:
            case COMMA:
                params.add(name2);
                args.add(new Variable(name2, curLine));
                ans = parseOtherArgs1(params, args, name1);
                break;
            case LPAR1:
                lex.nextToken();
                args.add(new CallExpression(parseArgumentList(), name2, curLine));
                parseOtherArgs2(args);
                ans = new Pair<>(new HashMap<>(), new CallExpression(args, name1, curLine));
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private Pair<Map<String, Function>, Expression> parseOtherArgs1(List<String> params, List<Expression> args, String name) throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case COMMA:
                lex.nextToken();
                ans = parseArgsOrParams(params, args, name);
                break;
            case RPAR1:
                lex.nextToken();
                ans = parseFunctionBody(params, args, name);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private Pair<Map<String, Function>, Expression> parseFunctionBody(List<String> params, List<Expression> args, String name) throws ParseException {
        Pair<Map<String, Function>, Expression> ans;
        switch (lex.getCurToken()) {
            case EQ:
                lex.nextToken();
                if (lex.getCurToken() != Token.LPAR3) {
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                Expression expression = parseExpression1();
                if (lex.getCurToken() != Token.RPAR3) {
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                if (lex.getCurToken() != Token.EOL) {
                    throw new ParseException("SYNTAX ERROR");
                }
                curLine++;
                lex.nextToken();
                Pair<Map<String, Function>, Expression> program = parseProgram();
                Map<String, Function> mp = program.getKey();
                mp.put(name, new Function(params, expression));
                ans = new Pair<>(mp, program.getValue());
                break;

            case END:
                ans = new Pair<>(new HashMap<>(), new CallExpression(args, name, curLine));
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private void parseOtherArgs2(List<Expression> args) throws ParseException {
        switch (lex.getCurToken()) {
            case COMMA:
                lex.nextToken();
                parseArgs(args);
                break;
            case RPAR1:
                lex.nextToken();
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }

        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
    }

    private void parseArgs(List<Expression> args) throws ParseException {
        switch (lex.getCurToken()) {
            case NUMBER:
            case LPAR1:
            case LPAR2:
            case MINUS:
                args.add(parseExpression2());
                parseOtherArgs2(args);
                break;
            case NAME:
                String name = lex.getName();
                lex.nextToken();
                parseCallOrOther2(args, name);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
    }

    private void parseCallOrOther2(List<Expression> args, String name) throws ParseException {
        switch (lex.getCurToken()) {
            case RPAR1:
            case COMMA:
                args.add(new Variable(name, curLine));
                parseOtherArgs2(args);
                break;
            case LPAR1:
                lex.nextToken();
                args.add(new CallExpression(parseArgumentList(), name, curLine));
                parseOtherArgs2(args);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.END) {
            throw new ParseException("SYNTAX ERROR");
        }
    }

    private List<Expression> parseArgumentList() throws ParseException {
        List<Expression> ans = new ArrayList<>();
        switch (lex.getCurToken()) {
            case MINUS:
            case NUMBER:
            case LPAR1:
            case LPAR2:
            case NAME:
                ans.add(parseExpression1());
                parseContArgumentList(ans);
                break;
        }
        if (lex.getCurToken() != Token.RPAR1) {
            throw new ParseException("SYNTAX ERROR");
        }
        return ans;
    }

    private void parseContArgumentList(List<Expression> args) throws ParseException {
        switch (lex.getCurToken()) {
            case COMMA:
                lex.nextToken();
                args.add(parseExpression1());
                parseContArgumentList(args);
                break;
            case RPAR1:
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        if (lex.getCurToken() != Token.RPAR1) {
            throw new ParseException("SYNTAX ERROR");
        }
    }

    private Expression parseExpression1() throws ParseException {
        Expression ans;
        switch (lex.getCurToken()) {
            case MINUS:
            case NUMBER:
            case LPAR1:
            case LPAR2:
                ans = parseExpression2();
                break;
            case NAME:
                String name = lex.getName();
                lex.nextToken();
                ans = parseCallExpression(name);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression parseCallExpression(String name) throws ParseException {
        Expression ans;
        switch (lex.getCurToken()) {
            case LPAR1:
                lex.nextToken();
                List<Expression> args = parseArgumentList();
                if (lex.getCurToken() != Token.RPAR1) {
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                ans = new CallExpression(args, name, curLine);
                break;
            case RPAR3:
            case RPAR2:
            case RPAR1:
            case COMMA:
            case MINUS:
            case PLUS:
            case DIV:
            case AST:
            case LT:
            case PER:
            case GT:
            case EQ:
                ans = new Variable(name, curLine);
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression parseExpression2() throws ParseException {
        Expression ans;
        switch (lex.getCurToken()) {
            case MINUS:
            case NUMBER:
                ans = parseConstantExpression();
                break;
            case LPAR1:
                ans = parseBinaryExpression();
                break;
            case LPAR2:
                ans = parseIfExpression();
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression parseIfExpression() throws ParseException {
        Expression ans;
        if (lex.getCurToken() == Token.LPAR2) {
            lex.nextToken();
            Expression iff = parseExpression1();
            if (lex.getCurToken() != Token.RPAR2) {
                throw new ParseException("SYNTAX ERROR");
            }
            lex.nextToken();
            if (lex.getCurToken() != Token.QMARK) {
                throw new ParseException("SYNTAX ERROR");
            }

            Expression first = ifBody();

            if (lex.getCurToken() != Token.ELSE) {
                throw new ParseException("SYNTAX ERROR");
            }

            Expression second = ifBody();

            ans = new IfExpression(iff, first, second, curLine);
        } else {
            throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression ifBody() throws ParseException {
        lex.nextToken();
        if (lex.getCurToken() != Token.LPAR1) {
            throw new ParseException("SYNTAX ERROR");
        }
        lex.nextToken();
        Expression expression = parseExpression1();
        if (lex.getCurToken() != Token.RPAR1) {
            throw new ParseException("SYNTAX ERROR");
        }
        lex.nextToken();
        return expression;
    }

    private Expression parseConstantExpression() throws ParseException {
        Expression ans;
        switch (lex.getCurToken()) {
            case MINUS:
                lex.nextToken();
                if (lex.getCurToken() != Token.NUMBER) {
                    throw new ParseException("SYNTAX ERROR");
                }
                ans = new Const(-lex.getNumber(), curLine);
                lex.nextToken();
                break;
            case NUMBER:
                ans = new Const(lex.getNumber(), curLine);
                lex.nextToken();
                break;
            default:
                throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression parseBinaryExpression() throws ParseException {
        Expression ans;
        if (lex.getCurToken() == Token.LPAR1) {
            lex.nextToken();
            Expression expression = parseExpression1();
            switch (lex.getCurToken()) {
                case PLUS:
                    lex.nextToken();
                    ans = new Add(expression, parseExpression1(), curLine);
                    break;
                case MINUS:
                    lex.nextToken();
                    ans = new Sub(expression, parseExpression1(), curLine);
                    break;
                case DIV:
                    lex.nextToken();
                    ans = new Div(expression, parseExpression1(), curLine);
                    break;
                case PER:
                    lex.nextToken();
                    ans = new Mod(expression, parseExpression1(), curLine);
                    break;
                case AST:
                    lex.nextToken();
                    ans = new Prod(expression, parseExpression1(), curLine);
                    break;
                case GT:
                    lex.nextToken();
                    ans = new GreaterThen(expression, parseExpression1(), curLine);
                    break;
                case LT:
                    lex.nextToken();
                    ans = new LessThan(expression, parseExpression1(), curLine);
                    break;
                case EQ:
                    lex.nextToken();
                    ans = new Equality(expression, parseExpression1(), curLine);
                    break;
                default:
                    throw new ParseException("SYNTAX ERROR");
            }

            if (lex.getCurToken() != Token.RPAR1) {
                throw new ParseException("SYNTAX ERROR");
            }
            lex.nextToken();
        } else {
            throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private void checkExpression() throws ParseException {
        if (lex.getCurToken() != Token.END &&
                lex.getCurToken() != Token.RPAR2 &&
                lex.getCurToken() != Token.RPAR3 &&
                lex.getCurToken() != Token.COMMA &&
                lex.getCurToken() != Token.RPAR1 &&
                lex.getCurToken() != Token.PLUS &&
                lex.getCurToken() != Token.MINUS &&
                lex.getCurToken() != Token.AST &&
                lex.getCurToken() != Token.DIV &&
                lex.getCurToken() != Token.PER &&
                lex.getCurToken() != Token.LT &&
                lex.getCurToken() != Token.GT &&
                lex.getCurToken() != Token.EQ
        ) throw new ParseException("SYNTAX ERROR");
    }


}
