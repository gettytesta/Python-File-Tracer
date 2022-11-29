/**
 * Getty Testa
 * 115217416
 * Recitation 01
 */

public class CodeBlock {
    public static String[] BLOCK_TYPES = { "def", "for", "while", "if", "elif", "else" };
    public static int DEF = 0;
    public static int FOR = 1;
    public static int WHILE = 2;
    public static int IF = 3;
    public static int ELIF = 4;
    public static int ELSE = 5;

    private String name;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String loopVariable;

    // Keeps track of how many blocks under the current block.
    // Used for 1.2, 1.3, etc.
    private int blockCount = 1;

    /**
     * Constructor for the CodeBlock class sets member variable to the users
     * inputs.
     * 
     * @param name    the name for the CodeBlock
     * @param block   the blockComplexity for the current block
     * @param highest the highest complexity in the lower blocks
     * @param loop    the name of the variable being changed, used in while loops
     */
    public CodeBlock(String name, Complexity block, Complexity highest, String loop) {
        this.name = name;
        blockComplexity = block;
        highestSubComplexity = highest;
        loopVariable = loop;
    }

    /**
     * Constructor for the CodeBlock class. Sets member variables to empty
     * or generic data.
     */
    public CodeBlock() {
        this("", new Complexity(), new Complexity(), null);
    }

    /**
     * Gets the name of the CodeBlock
     * 
     * @return the name of the CodeBlock
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of the CodeBlock
     * 
     * @param name the new name for the CodeBlock
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the blockComplexity for the CodeBlock
     * 
     * @return the blockComplexity of the CodeBlock
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * Changes the blockComplexity of the CodeBlock
     * 
     * @param block the new blockComplexity for the CodeBlock
     */
    public void setBlockComplexity(Complexity block) {
        blockComplexity = block;
    }

    /**
     * Gets the highestSubComplexity of the CodeBlock
     * 
     * @return the highestSubComplexity of the CodeBlock
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * Changes the highestSubComplexity of the CodeBlock
     * 
     * @param highest the new highestSubComplexity of the CodeBlock
     */
    public void setHighestSubComplexity(Complexity highest) {
        highestSubComplexity = highest;
    }

    /**
     * Gets the loopVaraible for the CodeBlock
     * 
     * @return the loopVariable for the CodeBlock
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * Changes the loopVariable for the CodeBlock
     * 
     * @param loop the new loopVariable for the CodeBlock
     */
    public void setLoopVariable(String loop) {
        loopVariable = loop;
    }

    /**
     * Gets the total complexity of the CodeBlock. The sum of the
     * highestSubComplexity
     * and the current blockComplexity.
     * 
     * @return the total complexity of the CodeBlock
     */
    public Complexity getTotalComplexity() {
        Complexity totalComp = new Complexity();
        totalComp.setLogPower(blockComplexity.getLogPower() + highestSubComplexity.getLogPower());
        totalComp.setNPower(blockComplexity.getNPower() + highestSubComplexity.getNPower());
        return totalComp;
    }

    /**
     * Gets the number of blocks under the current block. Used for
     * 1.2, 1.3, 1.1.2, etc.
     * 
     * @return the number of blocks under the current block
     */
    public int getBlockCount() {
        return blockCount;
    }

    /**
     * Increments the number of blocks under the current.
     */
    public void incrementBlockCount() {
        blockCount++;
    }

    /**
     * Creates and returns the string representation of CodeBlock, including
     * name and complexities.
     */
    public String toString() {
        String blockName = String.format("BLOCK %s:", name);
        String blockComp = String.format("block complexity = %s", blockComplexity.toString());
        String highestSubComp = String.format("highest sub-complexity = %s", highestSubComplexity.toString());
        return String.format("    %-15s%-30s%s\n", blockName, blockComp, highestSubComp);
    }
}