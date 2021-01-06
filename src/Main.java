import java.io.IOException;

public class Main {
    public static int lineCount;
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("Add.asm");

        while (parser.advance()) {
            if (parser.getCurrentCommand().matches("^$|^\\/\\/.*$")) {
                continue; // Skip comments and empty lines.
            }
            lineCount++;
            switch (parser.commandType()) {
                case "A_COMMAND":
                    String aCommand = parser.symbol();
                    System.out.println(Integer.toBinaryString(Integer.parseInt(aCommand)));
                    break;
                case "C_COMMAND":
                    String cCommand = null;

                    if (parser.comp().contains("m")) {
                        cCommand =
                                "1111" + Code.dest(parser.dest()) + Code.comp(parser.comp()) + Code.jump(parser.jump());
                    } else {
                        cCommand =
                                "1110" + Code.dest(parser.dest()) + Code.comp(parser.comp()) + Code.jump(parser.jump());
                    }

                    System.out.println(cCommand);
                    break;
                case "L_COMMAND":
                    String lCommand = "";
                    break;
            }
        }
    }
}
