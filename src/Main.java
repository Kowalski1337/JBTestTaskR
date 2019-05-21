import exception.DivisionByZeroException;
import exception.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, DivisionByZeroException {
        Parser parser = new Parser(Files.newInputStream(Paths.get("test.txt") ));
        System.out.println(parser.parse().evaluate());
    }

}
