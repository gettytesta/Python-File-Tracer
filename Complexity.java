/**
 * Getty Testa
 * 115217416
 * Recitation 01
 */

public class Complexity {
    private int nPower;
    private int logPower;

    /**
     * Constructor for the Complexity class. Sets both powers to 0, for a
     * time complexity of O(1)
     */
    public Complexity() {
        nPower = 0;
        logPower = 0;
    }

    /**
     * Gets the power of N complexities in the block
     * 
     * @return the power of N complexities in the block
     */
    public int getNPower() {
        return nPower;
    }

    /**
     * Changes the power of N complexities in the block
     * 
     * @param nPower the new power of N complexities
     */
    public void setNPower(int nPower) {
        this.nPower = nPower;
    }

    /**
     * Gets the power of log_N complexities in the block
     * 
     * @return the power of log_N complexities in the block
     */
    public int getLogPower() {
        return logPower;
    }

    /**
     * Changes the power of log_N complexities in the block
     * 
     * @param logPower the new power of log_N complexiites
     */
    public void setLogPower(int logPower) {
        this.logPower = logPower;
    }

    /**
     * Creates and returns a string representation of a time complexity
     * 
     * @return the string representation of a time complexity
     */
    public String toString() {
        String complexityString = "";
        if (nPower != 0) {
            complexityString += "O(n";
            if (nPower > 1) {
                complexityString += "^" + nPower;
            }
            if (logPower != 0) {
                complexityString += " * log(n)";
            }
            if (logPower > 1) {
                complexityString += "^" + logPower;
            }
            complexityString += ")";
        } else if (logPower != 0) {
            complexityString += "O(log(N)";
            if (logPower == 1) {
                complexityString += ")";
            } else {
                complexityString += "^" + logPower + ")";
            }
        } else {
            complexityString += "O(1)";
        }
        return complexityString;
    }
}
