import exception.ParseException;
import exception.runTimeException.RunTimeException;
import expression.Expression;
import expression.function.Function;
import javafx.util.Pair;
import parser.Parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            Parser parser = new Parser(Files.newBufferedReader(Paths.get("test.txt"), Charset.forName("UTF-8")));
            Pair<Map<String, Function>, Expression> ans = parser.parse();

            System.out.println(ans.getValue().evaluate(ans.getKey(), new HashMap<>()));
        } catch (RunTimeException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

}
