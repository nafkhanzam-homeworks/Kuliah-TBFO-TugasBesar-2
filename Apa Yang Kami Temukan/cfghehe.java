package parser;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import static java.util.Objects.requireNonNull;

/**
 * A translator that converts simple arithmetic expressions into postfix form.
 *
 * The context free grammar is:
 *
 * expr -> expr + term  { print('+') }
 *       | expr - term  { print('-') }
 *       | term
 *
 * term -> 0  { print('0') }
 *       | 1  { print ('1') }
 *         ...
 *       | 9 { print('9') }
 *
 * After applying left-recursion-eliminating transformation to prevent infinite recursion the grammar is:
 *
 * expr -> term rest
 *
 * rest -> + term { print('+') } rest
 *       | - term { print('-') } rest
 *       | e
 *
 * term -> 0 { print('0') }
 *       | 1 { print('1') }
 *         ...
 *       | 9 { print('9') }
 */
public class Parser {
    private final Reader input;
    private final Writer output;
    private int lookahead;

    public Parser(Reader input, Writer output) throws IOException {
        this.input = requireNonNull(input);
        this.output = requireNonNull(output);
        this.lookahead = this.input.read();
    }

    /**
     * Implements the expr case of the transformed grammar.
     * The expr case is: `expr -> term rest`.
     * That means that that first the term() method is called to handle the
     * term case then the rest() method is called to handle the rest case.
     * @throws IOException
     */
    public void expr() throws IOException {
        this.term();
        this.rest();
    }

    /**
     * Implements the term case of the transformed grammar.
     * The term case is: `i { print('i') }` with i being a digit from 0 to 9.
     * This means that if the lookahead character i is a digit, then read the next char by calling
     * match(i) and then write out i into the output input.
     * @throws IOException
     */
    public void term() throws IOException {
        if (Character.isDigit((char) this.lookahead)) {
            int lookahead = this.lookahead;
            match(lookahead);
            output.write(lookahead);
        } else {
            throw new Error("Syntax error");
        }
    }

    /**
     * Implements the rest case of the transformed grammar.
     * The rest case is:
     * ` rest -> + term { print('+') } rest
     *         | - term { print('-') } rest
     *         | e `
     * This means that if the lookahead character is a '+' or '-' then it will
     * read the next character, call the term() case, print out '+' or '-', then recursively call
     * the rest() case. If the lookahead character is not a '+' or '-' it does nothing.
     *
     * This code could be written exactly like the grammer by recursively calling rest() over and over
     * but rewriting the logic to use an infinite loop will make it less likely to overflow the stack.
     * The rewritten logic loops forever and only breaks out if the lookahead character is not a '+' or '-'.
     * @throws IOException
     */
    public void rest() throws IOException {
        while (true) {
            switch (lookahead) {
                case '+':
                    this.match('+');
                    this.term();
                    this.output.write('+');
                    break;
                case '-':
                    this.match('-');
                    this.term();
                    this.output.write('-');
                    break;
                default:
                    return; // Break out of the loop if not '+' or '-'.
            }
        }
    }

    /**
     * Checks if the character passed in matches the lookahead character and
     * reads the next character into the lookahead.
     * @param ch
     * @throws IOException
     */
    public void match(int ch) throws IOException {
        if (lookahead == ch) {
            lookahead = this.input.read();
        } else {
            throw new Error("Syntax Error");
        }
    }
}
