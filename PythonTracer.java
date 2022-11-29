
/**
 * Getty Testa 
 * 115217416
 * Recitation 01
 */

import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;

public class PythonTracer {
    static final int SPACE_COUNT = 4;

    /**
     * Prompts the user for the name of a file containing a single Python
     * function, determines its order of complexity, and prints the result
     * to the console.
     * 
     * @param args the command line arguments for the program
     */
    public static void main(String[] args) {
        boolean isTerminated = false;
        Scanner fileReader = new Scanner(System.in);

        while (!isTerminated) {
            System.out.print("Please enter a file name (or 'quit' to quit): ");
            String userInput = fileReader.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                isTerminated = true;
                continue;
            }

            Complexity userProgramComplexity = traceFile(userInput);
            if (userProgramComplexity != null) {
                System.out.printf("Overall complexity of %s: %s\n", userInput, userProgramComplexity.toString());
            }
        }
        fileReader.close();
        System.out.println("Program terminating successfully...\n");
    }

    /**
     * Opens the indicated file and traces through the code of the Python
     * function contained within the file, returning the order of complexity
     * of the function. During operation, the stack trace should be printed
     * to the console as code blocks are pushed to/popped from the stack.
     * 
     * Preconditions: filename is not null and the file it names contains a
     * single Python function with valid syntax
     * 
     * @param file the file to be traced
     * @return a Complexity object representing the total order of complexity of the
     *         Python program
     */
    public static Complexity traceFile(String file) {
        if (file == null) {
            System.out.println("File name does not exist!");
            return null;
        }

        Stack<CodeBlock> blockStack = new Stack<CodeBlock>();
        String blockLevel = "1";
        int holdBlockCount;

        try {
            Scanner reader = new Scanner(new File(file));
            String currentLine;
            String trimmedLine;
            String[] splitLine;

            while (reader.hasNextLine()) {
                currentLine = reader.nextLine();
                trimmedLine = currentLine.trim();
                splitLine = trimmedLine.split(" ");

                if (trimmedLine.length() != 0 && !(trimmedLine.charAt(0) == 35)) {
                    int indentCount = (currentLine.length() - trimmedLine.length()) / SPACE_COUNT;
                    while (indentCount < blockStack.size()) {
                        if (indentCount == 0) {
                            reader.close();
                            System.out.println("Leaving block 1.");
                            return blockStack.peek().getTotalComplexity();
                        } else {
                            CodeBlock oldTop = blockStack.pop();
                            blockStack.peek().incrementBlockCount();
                            System.out.printf("Leaving block %s", oldTop.getName());
                            Complexity oldTopComplexity = oldTop.getTotalComplexity();

                            Complexity topComplexity = blockStack.peek().getHighestSubComplexity();

                            if (oldTopComplexity.getNPower() > topComplexity.getNPower()) {
                                blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                                System.out.printf(", updating block %s:\n", blockStack.peek().getName());
                            } else if (oldTopComplexity.getNPower() == topComplexity.getNPower()) {
                                if (oldTopComplexity.getLogPower() > topComplexity.getLogPower()) {
                                    blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                                    System.out.printf(", updating block %s:\n", blockStack.peek().getName());
                                } else {
                                    System.out.println(", nothing to update.");
                                }
                            } else {
                                System.out.println(", nothing to update.");
                            }
                            blockLevel = blockStack.peek().getName();
                            System.out.println(blockStack.peek().toString());
                        }
                    }

                    boolean containsKeyword = false;
                    for (int i = 0; i < 6; i++) {
                        if (splitLine[0].replaceAll("[^a-zA-Z]", "").equals(CodeBlock.BLOCK_TYPES[i])) {
                            containsKeyword = true;
                            break;
                        }
                    }
                    if (containsKeyword) {
                        if (blockStack.isEmpty()) {
                            holdBlockCount = 1;
                        } else {
                            holdBlockCount = blockStack.peek().getBlockCount();
                            blockLevel += String.format(".%s", holdBlockCount);
                        }
                        System.out.printf("Entering block %s '%s':\n", blockLevel,
                                splitLine[0].replaceAll("[^a-zA-Z]", ""));
                        CodeBlock newBlock = new CodeBlock();
                        newBlock.setName(blockLevel);

                        if (splitLine[0].equals(CodeBlock.BLOCK_TYPES[CodeBlock.FOR])) {
                            // We skip checking for "log_N:" because there are only 2 possible options
                            if (splitLine[3].equals("N:")) {
                                newBlock.getBlockComplexity().setNPower(1);
                                blockStack.push(newBlock);
                            } else {
                                newBlock.getBlockComplexity().setLogPower(1);
                                blockStack.push(newBlock);
                            }
                        } else if (splitLine[0].equals(CodeBlock.BLOCK_TYPES[CodeBlock.WHILE])) {
                            newBlock.setLoopVariable(splitLine[1]);
                            blockStack.push(newBlock);
                        } else {
                            blockStack.push(newBlock);
                        }
                        System.out.println(newBlock.toString());
                    } else if (!blockStack.isEmpty() && blockStack.peek().getLoopVariable() != null) {
                        if (trimmedLine.contains(String.format("%s /= 2", blockStack.peek().getLoopVariable()))) {
                            System.out.printf("Found update statement, updating block %s:\n",
                                    blockStack.peek().getName());
                            blockStack.peek().getBlockComplexity().setLogPower(1);
                            System.out.println(blockStack.peek().toString());
                        } else if (trimmedLine
                                .contains(String.format("%s -= 1", blockStack.peek().getLoopVariable()))) {
                            System.out.printf("Found update statement, updating block %s:\n", blockLevel);
                            blockStack.peek().getBlockComplexity().setNPower(1);
                            System.out.println(blockStack.peek().toString());
                        }
                    }
                } else {
                    continue;
                }
            }
            while (blockStack.size() > 1) {
                CodeBlock oldTop = blockStack.pop();
                System.out.printf("Leaving block %s", oldTop.getName());
                Complexity oldTopComplexity = oldTop.getTotalComplexity();

                Complexity topComplexity = blockStack.peek().getHighestSubComplexity();

                if (oldTopComplexity.getNPower() > topComplexity.getNPower()) {
                    blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                    System.out.printf(", updating block %s:\n", blockStack.peek().getName());
                } else if (oldTopComplexity.getNPower() == topComplexity.getNPower()) {
                    if (oldTopComplexity.getLogPower() > topComplexity.getLogPower()) {
                        blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                        System.out.printf(", updating block %s:\n", blockStack.peek().getName());
                    } else {
                        System.out.println(", nothing to update.");
                    }
                } else {
                    System.out.println(", nothing to update.");
                }
                blockLevel = blockStack.peek().getName();
                System.out.println(blockStack.peek().toString());
            }
            System.out.println("Leaving block 1.\n");
            return blockStack.pop().getTotalComplexity();

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found...\n");
        }
        return null;
    }
}
