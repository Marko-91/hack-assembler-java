import java.util.HashMap;
import java.util.Map;

/**************************************************************************
 * SYMBOL TABLE
 * Keeps a correspondence between symbolic labels and numeric addresses.
 **************************************************************************/
public class SymbolTable {
    private Map <String, String> table;

    public SymbolTable() {
        table = new HashMap<>() {{
            put("SP", "0");
            put("LCL", "1");
            put("ARG", "2");
            put("THIS", "3");
            put("THAT", "4");
            put("SCREEN", "16384");
            put("KBD", "24576");
            put("R0", "0");
            put("R1", "1");
            put("R2", "2");
            put("R3", "3");
            put("R4", "4");
            put("R5", "5");
            put("R6", "6");
            put("R7", "7");
            put("R8", "8");
            put("R9", "9");
            put("R10", "10");
            put("R11", "11");
            put("R12", "12");
            put("R13", "13");
            put("R14", "14");
            put("R15", "15");
        }};
    }

    public void addEntry(String symbol, String address) {
        table.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public String getAddress(String symbol) {
        return table.get(symbol);
    }
}
