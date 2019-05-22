package lexer;

import exception.ParseException;

import java.io.BufferedReader;
import java.io.IOException;

public class Lexer {
    private BufferedReader br;
    private int curChar;
    private String name;
    private Token curToken;
    private int curPos;
    private int number;

    public Lexer(BufferedReader br) throws ParseException {
        curPos = 0;
        this.br = br;
        nextChar();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = br.read();
        } catch (IOException e) {
            throw new ParseException("Problem was occurred while reading", curPos);
        }
    }

    private boolean isLetter(int c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';
    }

    private boolean isNumber(int c) {
        return c >= '0' && c <= '9';
    }

    public void nextToken() throws ParseException {
        if (isLetter(curChar)) {
            StringBuilder sb = new StringBuilder();
            sb.append((char) curChar);
            nextChar();
            while (isLetter(curChar)) {
                sb.append((char) curChar);
                nextChar();
            }
            name = sb.toString();
            curToken = Token.NAME;
            return;
        }

        if (isNumber(curChar)) {
            StringBuilder sb = new StringBuilder();
            sb.append((char) curChar);
            nextChar();
            while (isNumber(curChar)) {
                sb.append((char) curChar);
                nextChar();
            }
            number = Integer.parseInt(sb.toString());
            curToken = Token.NUMBER;
            return;
        }
        switch (curChar) {
            case '(':
                curToken = Token.LPAR1;
                nextChar();
                break;
            case ')':
                curToken = Token.RPAR1;
                nextChar();
                break;
            case '[':
                curToken = Token.LPAR2;
                nextChar();
                break;
            case ']':
                curToken = Token.RPAR2;
                nextChar();
                break;
            case '{':
                curToken = Token.LPAR3;
                nextChar();
                break;
            case '}':
                curToken = Token.RPAR3;
                nextChar();
                break;
            case ',':
                curToken = Token.COMMA;
                nextChar();
                break;
            case '=':
                curToken = Token.EQ;
                nextChar();
                break;
            case ':':
                curToken = Token.ELSE;
                nextChar();
                break;
            case '+':
                curToken = Token.PLUS;
                nextChar();
                break;
            case '-':
                curToken = Token.MINUS;
                nextChar();
                break;
            case '*':
                curToken = Token.AST;
                nextChar();
                break;
            case '/':
                curToken = Token.DIV;
                nextChar();
                break;
            case '%':
                curToken = Token.PER;
                nextChar();
                break;
            case '>':
                curToken = Token.GT;
                nextChar();
                break;
            case '<':
                curToken = Token.LT;
                nextChar();
                break;
            case '\n':
                curToken = Token.EOL;
                nextChar();
                break;
            case '?':
                curToken = Token.QMARK;
                nextChar();
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal symbol " + (char) curChar, curPos);
        }

    }

    public String getName() {
        return name;
    }

    public Token getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public int getNumber() {
        return number;
    }

    public String showToken() {
        switch (curToken) {
            case EQ:
                return "=";
            case GT:
                return ">";
            case LT:
                return "<";
            case AST:
                return "*";
            case DIV:
                return "/";
            case EOL:
                return "new line";
            case PER:
                return "%";
            case ELSE:
                return ":";
            case NAME:
                return "identifier: " + getName();
            case PLUS:
                return "+";
            case COMMA:
                return ",";
            case LPAR1:
                return "(";
            case LPAR2:
                return "[";
            case LPAR3:
                return "{";
            case MINUS:
                return "-";
            case QMARK:
                return "?";
            case RPAR1:
                return ")";
            case RPAR2:
                return "]";
            case RPAR3:
                return "}";
            case NUMBER:
                return "number: " + getNumber();
            case END:
                return "end of input";
            default:
                return "";
        }
    }
}
