import exception.ParseException;
import expression.*;
import lexer.Lexer;
import lexer.Token;

import java.io.InputStream;

public class Parser {
    private Lexer lex;

    public Parser(InputStream is) throws ParseException {
        lex = new Lexer(is);
    }

    public Expression parse() throws ParseException {
        lex.nextToken();
        return parseProgram();
    }

    private Expression parseProgram() throws ParseException {
        Expression ans;
        switch (lex.getCurToken()){
            case NUMBER:
            case MINUS:
            case LPAR1:
            case LPAR2:
                ans = parseExpression2();
                break;
            case NAME:
                default:
                    throw new ParseException("SYNTAX ERROR");
        }

        if (lex.getCurToken() != Token.END){
            throw new ParseException("SYNTAX ERROR");
        }

        return ans;
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
        switch (lex.getCurToken()){
            case LPAR2:
                lex.nextToken();
                Expression iff = parseExpression1();
                if (lex.getCurToken() != Token.RPAR2){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                if (lex.getCurToken() != Token.QMARK){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                if (lex.getCurToken() != Token.LPAR1){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                Expression first = parseExpression1();
                if (lex.getCurToken() != Token.RPAR1){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                if (lex.getCurToken() != Token.ELSE){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                if (lex.getCurToken() != Token.LPAR1){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                Expression second = parseExpression1();
                if (lex.getCurToken() != Token.RPAR1){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                ans = new IF(iff, first, second);
                break;
                default:
                    throw new ParseException("SYNTAX ERROR");
        }
        checkExpression();
        return ans;
    }

    private Expression parseConstantExpression() throws ParseException {
        Expression ans;
        switch (lex.getCurToken()){
            case MINUS:
                lex.nextToken();
                if (lex.getCurToken() != Token.NUMBER){
                    throw new ParseException("SYNTAX ERROR");
                }
                ans = new Const(-lex.getNumber());
                lex.nextToken();
                break;
            case NUMBER:
                ans = new Const(lex.getNumber());
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
        switch (lex.getCurToken()) {
            case LPAR1:
                lex.nextToken();
                Expression expression = parseExpression1();
                switch (lex.getCurToken()) {
                    case PLUS:
                        lex.nextToken();
                        ans = new Add(expression, parseExpression1());
                        break;
                    case MINUS:
                        lex.nextToken();
                        ans = new Sub(expression, parseExpression1());
                        break;
                    case DIV:
                        lex.nextToken();
                        ans = new Div(expression, parseExpression1());
                        break;
                    case PER:
                        lex.nextToken();
                        ans = new Mod(expression, parseExpression1());
                        break;
                    case AST:
                        lex.nextToken();
                        ans = new Prod(expression, parseExpression1());
                        break;
                    case GT:
                        lex.nextToken();
                        ans = new GreaterThen(expression, parseExpression1());
                        break;
                    case LT:
                        lex.nextToken();
                        ans = new LessThan(expression, parseExpression1());
                        break;
                    case EQ:
                        lex.nextToken();
                        ans = new Equality(expression, parseExpression1());
                        break;
                        default:
                            throw new ParseException("SYNTAX ERROR");
                }

                if (lex.getCurToken() != Token.RPAR1){
                    throw new ParseException("SYNTAX ERROR");
                }
                lex.nextToken();
                break;
            default:
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
