package tubes2tbfo;

import java.util.Objects;

/**
 * Pair
 */
public class Pair<A, B> {
    public A a;
    public B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return this.a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return this.b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public Pair<A, B> a(A a) {
        this.a = a;
        return this;
    }

    public Pair<A, B> b(B b) {
        this.b = b;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pair)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Pair<A, B> pair = (Pair<A, B>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "{" +
            " a='" + getA() + "'" +
            ", b='" + getB() + "'" +
            "}";
    }
}