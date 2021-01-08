import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static int lineCount;
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("Add.asm");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(parser.getFileName() + ".hack"), StandardCharsets.UTF_8))) {

            while (parser.advance()) {

                if (parser.getCurrentCommand().matches("^$|^\\/\\/.*$")) {
                    continue; // Skip comments and empty lines.
                }

                lineCount++; // Keep track of line counts.

                switch (parser.commandType()) {

                    case "A_COMMAND":

                        String aCommand = fillInMissingBinaries(parser.symbol());
                        writer.write(aCommand + "\n");
                        break;

                    case "C_COMMAND":

                        String cCommand = null;
                        if (parser.comp().contains("M")) { // If a=1 (write=1)
                            cCommand =
                                    "1111" + Code.dest(parser.dest()) + Code.comp(parser.comp()) + Code.jump(parser.jump());
                        } else { // If a=0 (write=0)
                            cCommand =
                                    "1110" + Code.dest(parser.dest()) + Code.comp(parser.comp()) + Code.jump(parser.jump());
                        }

                        writer.write(cCommand + "\n");

                        break;

                    case "L_COMMAND":

                        String lCommand = "";
                        writer.write(lCommand + "\n");
                        break;

                }
            }
        }
    }

    private static String fillInMissingBinaries(String symbol) {
        StringBuilder binaries = new StringBuilder(); // Store missing binaries here
        String stringifiedBinaryCode =
                Integer.toBinaryString(Integer.parseInt(symbol)); // Symbol as a binary string

        int zerosToAdd = 16 - stringifiedBinaryCode.length(); // 16 - number of binaries in HACK system (word length)
        binaries.append("0".repeat(Math.max(0, zerosToAdd))); // For each zero to add append 0

        return binaries + stringifiedBinaryCode;
    }
}
