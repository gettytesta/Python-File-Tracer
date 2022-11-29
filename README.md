# Python-File-Tracer
Most programming languages are organized as structured blocks of statements, with some blocks nested within others. Functions, which are examples of such blocks, execute statements and other blocks contained within them. Similarly, flow control structures, such as for and while loops, are blocks which can be executed several times subject to some condition. The Python programming language is an example of a language which follows this principle, and is even flexible enough to allow functions to be nested within functions!

In this homework assignment, you will create a code tracer program which takes the name of a Python file containing a single function and outputs the Big-Oh order of complexity of that function. To make things easier, several restrictions will be made on the format of the input code, and some techniques for text parsing will be described below. You must implement a BlockStack class to determine the complexity of blocks with nested blocks, and use the rules of Big-Oh complexity to determine the total complexity for the function. You are encouraged to use the Java API to help you complete this assignment, but you may implement the stack however you like.

It should be noted that you do not need to actually learn Python to complete this assignment - simply following the instructions as detailed in this specification should suffice. However, if you would like some background on language, a good starting point would be the Python website, which should provide you with enough information to get a feel for how the language works.

Required Classes
The following sections describe classes which are required for this assignment. Each section provides a description and the specifications necessary to complete each class. If you feel that additional methods would be useful, you may feel free to add them during your implementation as you see fit. However, all the variables and methods in the following specifications must be included in your project.

1. Complexity
Write a fully-documented class named Complexity which represents the Big-Oh complexity of some block of code. Since Big-Oh notation can get quite messy (e.g. log(n^(1/2) / n!)), we will restrict the possible orders to powers of two base types: n, and log_n. Following this practice, include two member variables in the Complexity class: nPower (int) and logPower (int). These two variables will keep track of what power each of the base types is present in the complexity object.

For example, a Complexity object with nPower= 4 and logPower = 2 would represent the complexity O(n^4 * log(n)^2). Similarly, a Complexity object with nPower = 1 and logPower = 1 would represent the complexity O(n * log(n)) (note that the exponents only appear when the power value is greater than 1). A case to consider is the situation in which both variables are 0, which indicates that the complexity is O(1).

public Complexity - constructor (you may include a constructor with parameters)

Two int member variables: nPower and logPower.

Getter and setter methods for both member variables.

A toString() method which prints human-readable Big-Oh notation. (e.g O(n^4 * log(n)^2).
2. CodeBlock
Write a fully-documented class named CodeBlock which describes a nested block of code. There are different types of code blocks you must consider (e.g. a for block, a while block, etc.), so create a static final array of six String variables named BLOCK_TYPES to enumerate the types of blocks available for nesting. "def", "for", "while", "if", "elif", "else". You should also declare six static final int variables corresponding to the indices of the BLOCK_TYPE array: DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5.

NOTE 1: When parsing Python code for the keywords, be careful not to accidentally parse the "for" in "fortune" (which might be a variable name) as the start of a for block. To avoid this, make sure there is a single space (" ") before and after the keyword before starting a new block.

NOTE 2: An alternative to the above combination of a single static array BLOCK_TYPES and six static final ints representing the indices is to use a Java Enum type. It is not required for this assignment, but you may find it to provide a simpler and more elegant solution.

Include a member variable blockComplexity (Complexity) to keep track of the Big-Oh complexity of this block, and a member variable highestSubComplexity (Complexity) to keep track of the Big-Oh complexity of the highest-order block nested within this block. The difference between these Complexity objects is that the blockComplexity represents the order of the block ignoring the statements inside (e.g. O(n) for a while block looping from n to 1), whereas the highestSubComplexity represents the highest complexity of all the blocks nested inside this block.

Lastly, include two String member variables name and loopVariable. The name member will be used to keep track of the nested structure of the blocks. The first block in the stack will always be named "1". All blocks included directly under a block will be numbered increasingly using a dot "." separator after the block's own name (e.g. blocks nested under block 1 will start with "1.1" and proceed to "1.2", "1.3", etc). Similarly, all blocks included directly under the block named "1.2" will be numbered "1.2.1", "1.2.2", "1.2.3", etc. For more detail, see the sample I/O below. The loopVariable member will only be used for while blocks in this assignment, as for blocks will not alter their variable during execution in the input code. In the constructor, this variable can be initialized to null and should only be updated when a while block is traced and the name of it's loop variable has been determined. See the sample algorithm below for further information.

Include getter and setter methods for all member variables.

public CodeBlock - constructor (you may include a constructor with parameters)

A static final array of six String constants BLOCK_TYPES representing the types of code blocks:
public static final String BLOCK_TYPES = {"def", "for", "while", "if", "elif", "else"};

Six static final int variables corresponding to the indices of the BLOCK_TYPES array:
public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5;

Two Complexity member variables:
private Complexity blockComplexity;
private Complexity highestSubComplexity;

Two String member variables:
private String name;
private String loopVariable;

Getter and setter methods for each member variable.
3. BlockStack (or java.util.Stack)
Since the complexity of each CodeBlock depends on the complexity of any CodeBlocks nested inside of it (following standard order of complexity rules), your tracer will need to use a Stack to determine the total complexity of a function. In this assignment, you can write your own Stack class or you may use the standard Stack class that is in the java.util package. Go to the HELP section and view the online documentation for Stack from the Java API.

CAUTION: Although the Stack class has push, pop and peek methods, the Stack class is a subclass of Vector. Therefore, all of the methods of Vector are also accessible in the Stack class. However, if you use any of the inherited Vector methods in your solution, you will be penalized in this assignment, since some of the Vector methods are not supposed to apply to a Stack ADT in general. (That is, the designers of Java basically define that a Stack is a special type of Vector, but it really isn't.)

4. PythonTracer
Write a fully-documented class named PythonTracer which contains a main method. The class will also contain a static final int variable SPACE_COUNT = 4 which will be used to determine the indentation of each statement (see below). In addition, include a static traceFile method, which will take as a parameter the name of a file containing a Python function. The traceFile method will open the indicated file, trace through the code of the Python function contained within the file, and output the details of the trace and the overall complexity to the console.

During the operation of the traceFile method, the user should be updated to the changes being made to the stack via a stack trace. Every time a new block is pushed on to the stack, the block's name (see the sample I/O for naming convention), complexity, and highest sub-complexity should be printed to the console. In addition, every time the Complexity at the top of the stack is updated, the new values should be printed to the console. Lastly, after a block is popped from the stack, the new top of the stack should be updated (it's highestSubComplexity variable should be changed if necessary), and it should be printed to the console, regardless of whether it was changed or not. These operations correspond to 'entering' a new block, determining the complexity of the current block, and 'leaving' a block, respectively. For more detail, see the sample I/O below.

public static final int SPACE_COUNT = 4;

public static Complexity traceFile(String filename)

Brief:
Opens the indicated file and traces through the code of the Python function contained within the file, returning the Big-Oh order of complexity of the function. During operation, the stack trace should be printed to the console as code blocks are pushed to/popped from the stack.

Preconditions:
filename is not null and the file it names contains a single Python function with valid syntax (Reminder: you do NOT have to check for invalid syntax).

Returns:
A Complexity object representing the total order of complexity of the Python code contained within the file.

public static void main(String[] args)
Brief:
Prompts the user for the name of a file containing a single Python function, determines its order of complexity, and prints the result to the console.

Trace Algorithm
A high-level algorithm for the traceFile method is given below:

    Initialize stack to an empty stack of CodeBlocks.
    Open file using filename.
    while file has lines
         line = next line in file.
        if line is not empty and line does not start with '#'
             indents = number of spaces in line / SPACES_COUNT.
            while indents is less than size of stack
                if indents is 0
                     Close file and return the total complexity of stack.top.
                else
                     oldTop = stack.pop()
                     oldTopComplexity = total complexity of oldTop
                    if oldTopComplexity is higher order than stack.top's highest sub-complexity
                         stack.top's highest sub-complexity = oldTopComplexity
            if line contains a keyword
                 keyword = keyword in line.
                if keyword is "for"
                    Determine the complexity at end of line ("N:" or "log_N:")
                    Create new O(n) or O(log(n)) CodeBlock and push onto stack.
                else if keyword is "while"
                     loopVariable = variable being updated (first token after "while").
                    Create new O(1) CodeBlock with loopVariable and push onto stack.
                else 
                    Create new O(1) CodeBlock and push onto the stack.
            else if stack.top is a "while" block and line updates stack.top's loopVariable 
                Update the blockComplexity of stack.top.
        else 
            Ignore line.
    while size of stack > 1
        oldTop = stack.pop()
        oldTopComplexity = total complexity of oldTop
        if oldTopComplexity is higher order than stack.top's highest sub-complexity
             stack.top's highest sub-complexity = oldTopComplexity
    Return stack.pop(). 
