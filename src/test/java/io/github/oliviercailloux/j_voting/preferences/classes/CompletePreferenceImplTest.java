package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

class CompletePreferenceImplTest {

    private CompletePreference getTwoClassesPreference()
                    throws DuplicateException, EmptySetException {
        return CompletePreferenceImpl.asCompletePreference(Voter.createVoter(3),
                        ImmutableList.of(
                                        ImmutableSet.of(Alternative.withId(1),
                                                        Alternative.withId(2)),
                                        ImmutableSet.of(Alternative
                                                        .withId(3))));
    }

    @Test
    void getRankTest() throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertEquals(1, toTest.getRank(Alternative.withId(1)));
        assertEquals(2, toTest.getRank(Alternative.withId(3)));
    }

    @Test
    public void getRankExceptionTest()
                    throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertThrows(Exception.class,
                        () -> toTest.getRank(Alternative.withId(4)));
    }

    @Test
    public void asCompletePreferenceTestException() {
        assertThrows(Exception.class, () -> CompletePreferenceImpl
                        .asCompletePreference(Voter.createVoter(1), null));
    }

    @Test
    public void asCompletePreferenceTestExceptionTow() {
        assertThrows(Exception.class, () -> CompletePreferenceImpl
                        .asCompletePreference(null, null));
    }

    @Test
    public void asCompletePreferenceTestEmptyList()
                    throws DuplicateException, EmptySetException {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        CompletePreference testCompletePreferenceImpl = CompletePreferenceImpl
                        .asCompletePreference(Voter.createVoter(3), empList);
        assertEquals(true,
                        testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest()
                    throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(1), Alternative.withId(2));
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest()
                    throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest()
                    throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>(
                        Arrays.asList(ImmutableSet.of(Alternative.withId(1),
                                        Alternative.withId(2)),
                                        ImmutableSet.of(Alternative
                                                        .withId(3))));
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest() throws DuplicateException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertEquals(Voter.createVoter(3), toTest.getVoter());
    }

    @Test
    public void createDuplicateException() throws DuplicateException {
        assertThrows(DuplicateException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(Voter.createVoter(3),
                            ImmutableList.of(ImmutableSet.of(
                                            Alternative.withId(1),
                                            Alternative.withId(2)),
                                            ImmutableSet.of(Alternative
                                                            .withId(2))));
        });
    }

    @Test
    public void createEmptySetException() throws DuplicateException {
        assertThrows(EmptySetException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(Voter.createVoter(3),
                            ImmutableList.of(ImmutableSet.of(), ImmutableSet
                                            .of(Alternative.withId(2))));
        });
    }
}
