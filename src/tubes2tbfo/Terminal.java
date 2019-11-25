package tubes2tbfo;

public class Terminal {
    public String terminal;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((terminal == null) ? 0 : terminal.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Terminal other = (Terminal) obj;
        if (terminal == null) {
            if (other.terminal != null)
                return false;
        } else if (!terminal.equals(other.terminal))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return terminal;
    }
}