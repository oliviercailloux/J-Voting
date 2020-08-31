package io.github.oliviercailloux.j_voting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Voter;

public class VoterTest {

    /**
     * Tests whether method getId() returns the value of the id declared at the
     * creation of this Voter
     */
    @Test
    public void testGetId() {
        Voter v = Voter.withId(4);
        assertEquals(v.getId(), 4);
    }

    /**
     * Tests whether method equals() applied in both ways to two Voters with the
     * same ids returns true
     */
    @Test
    public void testEquals() {
        Voter v = Voter.withId(3);
        Voter v2 = Voter.withId(3);
        assertEquals(v, v2);
    }

    @Test
    public void testCompareTo() {
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(3);
        assertTrue(v1.compareTo(v2) < 0);
    }
}
