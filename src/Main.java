import java.io.IOException;

public class Main {
    public static int lineCount;
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("Add.asm");

        while (parser.advance()) {
            if (parser.getCurrentCommand().matches("^$|^\\/\\/.*$")) {
                continue; // Skip comments and empty lines.
            }
            lineCount++; // Keep track of line counts.
            switch (parser.commandType()) {
                case "A_COMMAND":
                    String aCommand = fillInMissingBinaries(parser.symbol());
                    System.out.println(Integer.toBinaryString(Integer.parseInt(aCommand)));
                    break;
                case "C_COMMAND":
                    String cCommand = null;

                    if (parser.comp().contains("m")) { // If a=1 (write=1)
                        cCommand =
                                "1111" + Code.dest(parser.dest()) + Code.comp(parser.comp()) + Code.jump(parser.jump());
                    } else { // If a=0 (write=0)
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

    private static String fillInMissingBinaries(String symbol) {
        StringBuilder binaries = new StringBuilder("");
        int zerosToAdd = 16 - symbol.length(); // 16 - number of binaries in HACK system (word length)
        binaries.append("0".repeat(Math.max(0, zerosToAdd)));

        return binaries + symbol;
    }
}
