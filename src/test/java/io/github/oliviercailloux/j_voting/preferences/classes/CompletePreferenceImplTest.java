package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

class CompletePreferenceImplTest {

    private CompletePreference ifNeededByTest() {
        return CompletePreferenceImpl.asCompletePreference(Voter.createVoter(3),
                        ImmutableList.of(
                                        ImmutableSet.of(Alternative.withId(1),
                                                        Alternative.withId(2)),
                                        ImmutableSet.of(Alternative
                                                        .withId(3))));
    }

    @Test
    void getRankTest() {
        CompletePreference toTest = ifNeededByTest();
        assertEquals(1, toTest.getRank(Alternative.withId(1)));
        assertEquals(2, toTest.getRank(Alternative.withId(3)));
    }

    @Test
    public void getRankExceptionTest() {
        CompletePreference toTest = ifNeededByTest();
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
    public void asCompletePreferenceTestEmptyList() {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        CompletePreference testCompletePreferenceImpl = CompletePreferenceImpl
                        .asCompletePreference(Voter.createVoter(3), empList);
        assertEquals(true,
                        testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest() {
        CompletePreference toTest = ifNeededByTest();
        assertEquals("[1, 2]", toTest.getAlternatives(1).toString());
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(1), Alternative.withId(2));
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest() {
        CompletePreference toTest = ifNeededByTest();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest() {
        CompletePreference toTest = ifNeededByTest();
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(1), Alternative.withId(2));
        ImmutableSet<Alternative> immutableSet2 = ImmutableSet
                        .of(Alternative.withId(3));
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>();
        list.add(immutableSet);
        list.add(immutableSet2);
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest() {
        CompletePreference toTest = ifNeededByTest();
        assertEquals(Voter.createVoter(3), toTest.getVoter());
    }
}
