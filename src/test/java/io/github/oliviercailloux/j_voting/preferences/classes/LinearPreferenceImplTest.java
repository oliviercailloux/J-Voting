package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

class LinearPreferenceImplTest {

    private static LinearPreference getTwoClasses()
                    throws EmptySetException, DuplicateValueException {
        return LinearPreferenceImpl.asLinearPreference(Voter.createVoter(3),
                        ImmutableList.of(ImmutableSet.of(Alternative.withId(1)),
                                        ImmutableSet.of(Alternative.withId(3)),
                                        ImmutableSet.of(Alternative
                                                        .withId(2))));
    }

    @Test
    void testAsLinearPreferenceThrowsIllegalArgumentException()
                    throws DuplicateValueException, EmptySetException {
        assertThrows(IllegalArgumentException.class,
                        () -> LinearPreferenceImpl.asLinearPreference(
                                        Voter.createVoter(3),
                                        ImmutableList.of(ImmutableSet.of(
                                                        Alternative.withId(1),
                                                        Alternative.withId(2)),
                                                        ImmutableSet.of(Alternative
                                                                        .withId(3)))));
    }

    @Test
    void testlistToLinearPreference()
                    throws EmptySetException, DuplicateValueException {
        LinearPreference toTest = getTwoClasses();
        assertEquals(ImmutableList.of(Alternative.withId(1),
                        Alternative.withId(3), Alternative.withId(2)),
                        toTest.asList());
    }

    @Test
    void testAsList() throws EmptySetException, DuplicateValueException {
        LinearPreference toTest = getTwoClasses();
        assertEquals(ImmutableList.of(Alternative.withId(1),
                        Alternative.withId(3), Alternative.withId(2)),
                        toTest.asList());
    }

    @Test
    void throwsTest() {
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .asLinearPreference(null, ImmutableList.of(null)));
        assertThrows(Exception.class,
                        () -> LinearPreferenceImpl.asLinearPreference(
                                        Voter.createVoter(1),
                                        ImmutableList.of(null)));
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .asLinearPreference(Voter.createVoter(1), null));
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .listAlternativesToLinearPreference(
                                        Voter.createVoter(1),
                                        ImmutableList.of(null)));
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .listAlternativesToLinearPreference(
                                        Voter.createVoter(1), null));
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .listAlternativesToLinearPreference(null,
                                        ImmutableList.of(null)));
    }
}
