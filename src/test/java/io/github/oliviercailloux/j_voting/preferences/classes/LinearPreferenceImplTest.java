package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

class LinearPreferenceImplTest {

    private LinearPreference getThreeClassesPreference()
                    throws DuplicateValueException {
        return LinearPreferenceImpl.asLinearPreference(Voter.createVoter(3),
                        ImmutableList.of(Alternative.withId(1),
                                        Alternative.withId(2),
                                        Alternative.withId(3)));
    }

    @Test
    void getRankTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertEquals(1, toTest.getRank(Alternative.withId(1)));
        assertEquals(2, toTest.getRank(Alternative.withId(2)));
        assertEquals(3, toTest.getRank(Alternative.withId(3)));
    }

    @Test
    public void getRankExceptionTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertThrows(Exception.class,
                        () -> toTest.getRank(Alternative.withId(4)));
    }

    @Test
    public void asLinearPreferenceTestException() {
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .asLinearPreference(Voter.createVoter(1), null));
    }

    @Test
    public void asLinearPreferenceeTestExceptionTow() {
        assertThrows(Exception.class, () -> LinearPreferenceImpl
                        .asLinearPreference(null, null));
    }

    @Test
    public void asLinearPreferenceTestEmptyList()
                    throws DuplicateValueException {
        List<Alternative> empList = Lists.newArrayList();
        LinearPreference testCompletePreferenceImpl = LinearPreferenceImpl
                        .asLinearPreference(Voter.createVoter(3), empList);
        assertEquals(true,
                        testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(2));
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertEquals(ImmutableList.copyOf(ImmutableSet.of(
                        ImmutableSet.of(Alternative.withId(1)),
                        ImmutableSet.of(Alternative.withId(2)),
                        ImmutableSet.of(Alternative.withId(3)))),
                        toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertEquals(Voter.createVoter(3), toTest.getVoter());
    }

    @Test
    public void asListTest() throws DuplicateValueException {
        LinearPreference toTest = getThreeClassesPreference();
        assertEquals(ImmutableList.of(1, 2, 3).toString(),
                        toTest.asList().toString());
    }

    @Test
    public void asLinearPreferenceDuplicateExceptionTest()
                    throws DuplicateValueException, EmptySetException {
        assertThrows(DuplicateValueException.class, () -> {
            LinearPreferenceImpl.asLinearPreference(Voter.createVoter(3),
                            ImmutableList.of(Alternative.withId(1),
                                            Alternative.withId(2),
                                            Alternative.withId(2)));
        });
    }
}
