package io.github.oliviercailloux.j_voting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;

public class AlternativeTest {

    /**
     * Tests whether method getId() returns the value of the id declared at the
     * creation of this Alternative
     */
    @Test
    public void testGetId() {
        Alternative a = Alternative.withId(7);
        assertEquals(a.getId(), 7);
    }

    /**
     * Tests whether method equals() returns true for two Alternatives having
     * the same id
     */
    @Test
    public void testEqualsAlternative() {
        Alternative a = Alternative.withId(7);
        Alternative b = Alternative.withId(7);
        assertEquals(a, b);
    }

    /**
     * Tests whether method toString() returns the id declared at the creation
     * of this Alternative
     */
    @Test
    public void testToString() {
        Alternative a = Alternative.withId(7);
        assertEquals(a.toString(), Integer.toString(7));
    }
}
