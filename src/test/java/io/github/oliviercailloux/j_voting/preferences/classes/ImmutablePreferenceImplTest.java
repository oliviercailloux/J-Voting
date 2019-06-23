package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.ImmutablePreference;

class ImmutablePreferenceImplTest {

    static ImmutablePreference getApreference() {
        Set<List<Alternative>> preferencesLists = ImmutableSet.of(
                        ImmutableList.of(Alternative.withId(1),
                                        Alternative.withId(4)),
                        ImmutableList.of(Alternative.withId(3),
                                        Alternative.withId(2)),
                        ImmutableList.of(Alternative.withId(4),
                                        Alternative.withId(1)),
                        ImmutableList.of(Alternative.withId(4),
                                        Alternative.withId(3)),
                        ImmutableList.of(Alternative.withId(5),
                                        Alternative.withId(3)));
        return ImmutablePreferenceImpl.asImmutablePreference(
                        Voter.createVoter(2), preferencesLists);
    }

    @Test
    void getAlternativesImmutablePreferenceTest() {
        ImmutablePreference toTestImmutablePreference = getApreference();
        assertEquals(ImmutableSet.of(Alternative.withId(1),
                        Alternative.withId(2), Alternative.withId(3),
                        Alternative.withId(4), Alternative.withId(5)),
                        toTestImmutablePreference.getAlternatives());
    }

    @Test
    void getVoterImmutablePreferenceTest() {
        ImmutablePreference toTestImmutablePreference = getApreference();
        assertEquals(Voter.createVoter(2),
                        toTestImmutablePreference.getVoter());
    }

    @Test
    void asGraphTest() {
        ImmutableGraph<Alternative> toTestImmutableGraph = getApreference()
                        .asGraph();
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(1),
                        Alternative.withId(4)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(1)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(5),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(3),
                        Alternative.withId(2)));
        assertFalse(toTestImmutableGraph.hasEdgeConnecting(
                        Alternative.withId(1), Alternative.withId(5)));
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(2))
                        .size() == 1);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(1))
                        .size() == 2);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(4))
                        .size() == 3);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(5))
                        .size() == 2);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(3))
                        .size() == 2);
    }

    @Test
    void asTranstiveGraphTest() {
        // Cast will be changed ASAP, need to add a new function in Preference
        // Interface
        ImmutablePreferenceImpl toTest = (ImmutablePreferenceImpl) getApreference();
        ImmutableGraph<Alternative> toTestImmutableGraph = toTest
                        .asTransitiveGraph();
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(1),
                        Alternative.withId(4)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(1)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(5),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(3),
                        Alternative.withId(2)));
        assertFalse(toTestImmutableGraph.hasEdgeConnecting(
                        Alternative.withId(1), Alternative.withId(5)));
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(2))
                        .size() == 1);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(1))
                        .size() == 4);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(4))
                        .size() == 4);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(5))
                        .size() == 3);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(3))
                        .size() == 2);
    }
}
