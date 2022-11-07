import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringCalculator {

    static Logger logger = Logger.getLogger("global");
    public StringCalculator () {}

    /**
     * @param numbers is the string that contains the list of input numbers
     * @return the sum of the numbers if delimiter is written between numbers, -1 else
     * @throws NegativeNumberException if 'numbers' contains at least one negative number
     */
    public int add(String numbers) throws NegativeNumberException {
        int sum = 0;
        if (!numbers.isEmpty()) {
            char defaultSplit = ',';
            //Check if user has set a custom delimiter and update it
            if (numbers.startsWith("//")) {
                numbers = numbers.replace("//", "");
                defaultSplit = numbers.charAt(0);
                numbers = numbers.substring(1);
            }
            String[] rows = numbers.split("\n");
            for (String row: rows) {
                if(!row.isEmpty()) {
                    if (row.startsWith(defaultSplit + "") || row.endsWith(defaultSplit + "")) {
                        logger.log(Level.INFO, "Input is incorrect: you must write the '" + defaultSplit + "' character between numbers");
                        return -1;
                    }
                    //The '\\' sequence is an escape sequence prefix in regex
                    String[] splittedNumbers = String.valueOf(defaultSplit).matches("[a-zA-Z0-9]") ? row.split(String.valueOf(defaultSplit)) : row.split("\\" + defaultSplit);
                    String negativeNumbers = "";
                    for (String splittedNumber: splittedNumbers) {
                        int intNumber = Integer.parseInt(splittedNumber);
                        if (intNumber > 1000)
                            continue;

                        if (intNumber < 0)
                            negativeNumbers += negativeNumbers.isEmpty() ? intNumber : ", " + intNumber;

                        if(negativeNumbers.isEmpty())
                            sum += intNumber;
                    }
                    if(!negativeNumbers.isEmpty())
                        throw new NegativeNumberException("Negatives not allowed: " + negativeNumbers);
                }
            }
        }
        return sum;
    }
}
