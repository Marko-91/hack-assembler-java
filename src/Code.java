/**************************************************************************
 * CODE MODULE
 * Translates Hack assembly language mnemonics into binary codes.
 **************************************************************************/
public class Code {
    public static String dest(String mnemonics) throws Exception {
        switch (mnemonics.toUpperCase()) {
            case "":
                return "000";
            case "M":
                return "001";
            case "D":
                return "010";
            case "MD":
                return "011";
            case "A":
                return "100";
            case "AM":
                return "101";
            case "AD":
                return "110";
            case "AMD":
                return "111";
            default:
                throw new Exception("Syntax error << "+ mnemonics + " >> on line " + Main.lineCount + ".");
        }
    }

    public static String comp(String mnemonics) throws Exception {
        switch (mnemonics.toUpperCase()) {
            case "0":
                return "101010";
            case "1":
                return "111111";
            case "-1":
                return "111010";
            case "D":
                return "001100";
            case "A": case "M":
                return "110000";
            case "!D":
                return "001101";
            case "!A": case "!M":
                return "110001";
            case "-D":
                return "001111";
            case "-A": case "-M":
                return "110011";
            case "D+1":
                return "011111";
            case "A+1": case "M+1":
                return "110111";
            case "D-1":
                return "001110";
            case "A-1": case "M-1":
                return "110010";
            case "D+A": case "D+M":
                return "000010";
            case "D-A": case "D-M":
                return "010011";
            case "A-D": case "M-D":
                return "000111";
            case "D&A": case "D&M":
                return "000000";
            case "D|A": case "D|M":
                return "010101";
            default:
                throw new Exception("Syntax error << "+ mnemonics + " >> on line " + Main.lineCount + ".");
        }
    }

    public static String jump(String mnemonics) throws Exception {
        switch (mnemonics.toUpperCase()) {
            case "":
                return "000";
            case "JGT":
                return "001";
            case "JEQ":
                return "010";
            case "JGE":
                return "011";
            case "JLT":
                return "100";
            case "JNE":
                return "101";
            case "JLE":
                return "110";
            case "JMP":
                return "111";
            default:
                throw new Exception("Syntax error << "+ mnemonics + " >> on line " + Main.lineCount + ".");
        }
    }
}
