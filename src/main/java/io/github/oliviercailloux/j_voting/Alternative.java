package io.github.oliviercailloux.j_voting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * This class is immutable Contains an integer which corresponds to a voting
 * possibility
 */
public class Alternative {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(Alternative.class.getName());
    private int id;

    /**
     * Factory method for Alternative
     * 
     * @param id <code> not null </code>
     * @return new Alternative
     */
    public static Alternative withId(int id) {
        LOGGER.debug("Alternative Factory");
        Preconditions.checkNotNull(id);
        return new Alternative(id);
    }

    private Alternative(int id) {
        LOGGER.debug("Alternative constructor");
        this.id = id;
    }

    /**
     * @return the id of the Alternative
     */
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        Alternative alter = (Alternative) o;
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
