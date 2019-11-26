package tubes2tbfo;

import java.util.Objects;

/**
 * Symbol
 */
public class Symbol {
    public static final Terminal EPSILON = new Terminal((char)0);
    public Object value;
    public Symbol(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Symbol value(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Symbol)) {
            return false;
        }
        Symbol symbol = (Symbol) o;
        return Objects.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "{" +
            " value='" + getValue() + "'" +
            "}";
    }
}