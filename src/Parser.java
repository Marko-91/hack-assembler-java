/************************************************************************
 * PARSER MODULE
 * Encapsulates access to the input code. Reads an assembly language command,
 * parses it, and provides convenient access to the command’s components
 * (fields and symbols). In addition, removes all white space and comments.
 **************************************************************************/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    private String currentCommand;
    private final BufferedReader reader;
    private String fileName;

    public Parser(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        reader = new BufferedReader(new FileReader(this.fileName));
    }

    public boolean advance() throws IOException {
        return (currentCommand = reader.readLine()) != null;
    }

    public String commandType() {
        if (currentCommand.matches("\\s*@.+\\s*")) {
            return "A_COMMAND";
        }

        if (currentCommand.matches("\\s*\\([A-Za-z0-9]+\\)\\s*")) {
            return "L_COMMAND";
        }

        return "C_COMMAND";
    }

    public String symbol() {
        return currentCommand.replaceAll("@|\\(\\)", "");
    }

    public String dest() {
        String pattern = "([A-Z]+(?==))";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(currentCommand);
        return (matcher.find()) ? matcher.group() : "";
    }

    public String comp() {
        String pattern = "((?<==)[-+!&|A-Z]+(?=;?))";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(currentCommand);
        return (matcher.find()) ? matcher.group() : "";
    }

    public String jump() {
        String pattern = "((?<=;)[A-Z]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(currentCommand);
        return (matcher.find()) ? matcher.group() : "";
    }

    public String getCurrentCommand() {
        return currentCommand;
    }

    public void setCurrentCommand(String currentCommand) { this.currentCommand = currentCommand; }

    public String getFileName() {
        return fileName.replaceFirst(".asm", "");
    }

    public void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
