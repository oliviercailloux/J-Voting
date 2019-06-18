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
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

class LinearPreferenceImplTest {

    private LinearPreference ifNeededByTest() {
        return LinearPreferenceImpl.asLinearPreference(Voter.createVoter(3),
                        ImmutableList.of(Alternative.withId(1),
                                        Alternative.withId(2),
                                        Alternative.withId(3)));
    }

    @Test
    void getRankTest() {
        LinearPreference toTest = ifNeededByTest();
        assertEquals(1, toTest.getRank(Alternative.withId(1)));
        assertEquals(2, toTest.getRank(Alternative.withId(2)));
        assertEquals(3, toTest.getRank(Alternative.withId(3)));
    }

    @Test
    public void getRankExceptionTest() {
        LinearPreference toTest = ifNeededByTest();
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
    public void asLinearPreferenceTestEmptyList() {
        List<Alternative> empList = new ArrayList<>();
        LinearPreference testCompletePreferenceImpl = LinearPreferenceImpl
                        .asLinearPreference(Voter.createVoter(3), empList);
        assertEquals(true,
                        testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest() {
        LinearPreference toTest = ifNeededByTest();
        assertEquals("[2]", toTest.getAlternatives(1).toString());
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(2));
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest() {
        LinearPreference toTest = ifNeededByTest();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest() {
        LinearPreference toTest = ifNeededByTest();
        ImmutableSet<Alternative> immutableSet = ImmutableSet
                        .of(Alternative.withId(1));
        ImmutableSet<Alternative> immutableSet2 = ImmutableSet
                        .of(Alternative.withId(2));
        ImmutableSet<Alternative> immutableSet3 = ImmutableSet
                        .of(Alternative.withId(3));
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>();
        list.add(immutableSet);
        list.add(immutableSet2);
        list.add(immutableSet3);
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest() {
        LinearPreference toTest = ifNeededByTest();
        assertEquals(Voter.createVoter(3), toTest.getVoter());
    }

    @Test
    public void asListTest() {
        LinearPreference toTest = ifNeededByTest();
        assertEquals(ImmutableList.of(1, 2, 3).toString(),
                        toTest.asList().toString());
    }
}
