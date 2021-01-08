import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static int lineCount;
    public static int ROMAddressCount = 1;
    public static int RAMAddress = 16;

    public static void main(String[] args) throws Exception {
        if (args.length != 1 && !args[0].matches("\\.asm$")) {
            System.out.println("Bad input.\n$ java Main fileName.asm\nThe program will now exit");
            System.exit(1);
        }
        Parser labelParser = new Parser(args[0]);
        Parser parser = new Parser(args[0]);
        SymbolTable symbolTable = new SymbolTable();

        // FIRST PASS
        while (labelParser.advance()) {
            if (labelParser.getCurrentCommand().matches("^$|^\\/\\/.*$")) {
                continue; // Skip comments and empty lines.
            }

            if (labelParser.commandType().equals("L_COMMAND") &&
                    !symbolTable.contains(labelParser.symbol().trim())) {

                String labelToAdd = labelParser.symbol();
                symbolTable.addEntry(labelToAdd, String.valueOf(ROMAddressCount)); // Associate labels (labelName) with ROM addresses
                continue; // Do not add labels to rom address count
            }

            ROMAddressCount++;
        }
        labelParser.closeReader();
        // SECOND PASS
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(parser.getFileName() + ".hack"), StandardCharsets.UTF_8))) {

            while (parser.advance()) {

                if (parser.getCurrentCommand().matches("^$|^\\/\\/.*$")) {
                    continue; // Skip comments and empty lines.
                }

                lineCount++; // Keep track of line counts.

                switch (parser.commandType()) { // Determine the command type

                    case "A_COMMAND":
                        if (symbolTable.contains(parser.symbol()) &&
                                parser.symbol().matches("^[A-Za-z_]+[0-9_]*")) { // Variables can start with letter only!

                            String aCommand = fillInMissingBinaries(
                                    symbolTable.getAddress(parser.symbol()));
                            writer.write(aCommand + "\n");
                            break;
                        }

                        if (!symbolTable.contains(parser.symbol()) &&
                                parser.symbol().matches("^[A-Za-z_]+[0-9_]*")) {
                            symbolTable.addEntry(parser.symbol(), String.valueOf(RAMAddress));
                            parser.setCurrentCommand(symbolTable.getAddress(parser.symbol()));
                            RAMAddress++;
                            String aCommand = fillInMissingBinaries(parser.symbol());
                            writer.write(aCommand + "\n");
                            break;
                        }

                        String aCommand = fillInMissingBinaries(parser.symbol());
                        writer.write(aCommand + "\n");
                        break;

                    case "C_COMMAND":

                        String cCommand;
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

                        writer.write(""); // Skip L command
                        break;

                }
            }

            parser.closeReader();

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
