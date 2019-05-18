package io.github.oliviercailloux.j_voting;

import com.google.common.base.Preconditions;

/**
 * This class is immutable Contains an integer which corresponds to a voting
 * possibility
 */
public class Alternative {

    private int id;

    /**
     * Creates a new Alternative with the id given as a parameter
     * 
     * @param id int <code>not null</code>
     */
    public Alternative(int id) {
        this.id = Preconditions.checkNotNull(id);
    }

    /**
     * @return the id of the Alternative
     */
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // Check not null
        if (o == null)
            return false;
        // Check class type and cast o
        if (this.getClass() != o.getClass())
            return false;
        Alternative alter = (Alternative) o;
        // check field
        return this.getId() == alter.getId();
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
