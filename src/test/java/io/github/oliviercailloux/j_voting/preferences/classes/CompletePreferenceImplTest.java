package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

class CompletePreferenceImplTest {

    static CompletePreferenceImpl toTest;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        HashSet<Alternative> set = new HashSet<>();
        HashSet<Alternative> set2 = new HashSet<>();
        set.add(Alternative.withId(1));
        set.add(Alternative.withId(2));
        set2.add(Alternative.withId(3));
        ImmutableSet<Alternative> immutableSet = ImmutableSet.copyOf(set);
        ImmutableSet<Alternative> immutableSet2 = ImmutableSet.copyOf(set2);
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>();
        list.add(immutableSet);
        list.add(immutableSet2);
        ImmutableList<ImmutableSet<Alternative>> prefImmutableList = ImmutableList
                        .copyOf(list);
        toTest = CompletePreferenceImpl.asCompletePreference(
                        Voter.createVoter(3), prefImmutableList);
    }

    @Test
    void getRankTest() {
        assertEquals(1, toTest.getRank(Alternative.withId(1)));
        assertEquals(2, toTest.getRank(Alternative.withId(3)));
    }

    @Test
    public void getRankExceptionTest() {
        try {
            toTest.getRank(Alternative.withId(4));
            fail("Should throw exception when Alternative is not found");
        } catch (Exception aExp) {
            assert (aExp) != null;
        }
    }

    @Test
    public void asCompletePreferenceTestException() {
        try {
            toTest.asCompletePreference(Voter.createVoter(1), null);
            fail("Should throw exception when giving empty list");
        } catch (Exception aExp) {
            assert (aExp) != null;
        }
    }

    @Test
    public void asCompletePreferenceTestExceptionTow() {
        try {
            toTest.asCompletePreference(null, null);
            fail("Should throw exception when giving not found Alternative");
        } catch (Exception aExp) {
            assert (aExp) != null;
        }
    }

    @Test
    public void asCompletePreferenceTestEmptyList() {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        CompletePreferenceImpl testCompletePreferenceImpl = CompletePreferenceImpl
                        .asCompletePreference(Voter.createVoter(3), empList);
        assertEquals(true,
                        testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest() {
        assertEquals("[1, 2]", toTest.getAlternatives(1).toString());
        HashSet<Alternative> set = new HashSet<>();
        HashSet<Alternative> set2 = new HashSet<>();
        set.add(Alternative.withId(1));
        set.add(Alternative.withId(2));
        ImmutableSet<Alternative> immutableSet = ImmutableSet.copyOf(set);
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest() {
        try {
            toTest.getAlternatives(4);
            fail("Should throw exception when giving not found Alternativein given rank");
        } catch (ArrayIndexOutOfBoundsException aExp) {
            assert (aExp.getMessage().contains("3"));
        }
    }

    @Test
    public void asEquivalenceClassesTest() {
        HashSet<Alternative> set = new HashSet<>();
        HashSet<Alternative> set2 = new HashSet<>();
        set.add(Alternative.withId(1));
        set.add(Alternative.withId(2));
        set2.add(Alternative.withId(3));
        ImmutableSet<Alternative> immutableSet = ImmutableSet.copyOf(set);
        ImmutableSet<Alternative> immutableSet2 = ImmutableSet.copyOf(set2);
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>();
        list.add(immutableSet);
        list.add(immutableSet2);
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void asGraphTest() {
        assertEquals("isDirected: true, allowsSelfLoops: true, nodes: [1, 2, 3], edges: [<1 -> 1>, <1 -> 2>, <1 -> 3>, <2 -> 1>, <2 -> 2>, <2 -> 3>, <3 -> 3>]",
                        toTest.asGraph().toString());
    }

    @Test
    public void getVoterTest() {
        assertEquals(Voter.createVoter(3), toTest.getVoter());
    }
}
