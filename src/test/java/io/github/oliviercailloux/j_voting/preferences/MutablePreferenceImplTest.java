package io.github.oliviercailloux.j_voting.preferences;

import com.google.common.collect.Lists;
import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;
import org.eclipse.collections.impl.factory.Sets;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MutablePreferenceImplTest {
    
    @Test void of() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
    
        Set<Alternative> A = Sets.mutable.of(a1, a2);
        Set<Alternative> B = Sets.mutable.of(a3, a4);
        Set<Alternative> C = Sets.mutable.of(a5);
        
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B, C);
        Set<List<Set<Alternative>>> setTest = Sets.mutable.of(listTest);
    
    
        MutablePreferenceImpl pref = MutablePreferenceImpl.of(setTest, Voter.createVoter(1));
        MutableGraph G = pref.asGraph();
        assertEquals(G.nodes().containsAll(Sets.mutable.of(a1, a2, a3, a4, a5)), true);
        assertEquals(G.hasEdgeConnecting(a1, a1) &&
                        G.hasEdgeConnecting(a1, a2) &&
                        G.hasEdgeConnecting(a1, a3) &&
                        G.hasEdgeConnecting(a1, a5) &&
                        G.hasEdgeConnecting(a2, a1) &&
                        G.hasEdgeConnecting(a2, a2) &&
                        G.hasEdgeConnecting(a2, a3) &&
                        G.hasEdgeConnecting(a2, a4) &&
                        G.hasEdgeConnecting(a2, a5) &&
                        G.hasEdgeConnecting(a3, a3) &&
                        G.hasEdgeConnecting(a3, a4) &&
                        G.hasEdgeConnecting(a3, a5) &&
                        G.hasEdgeConnecting(a4, a3) &&
                        G.hasEdgeConnecting(a4, a4) &&
                        G.hasEdgeConnecting(a4, a5) &&
                        !G.hasEdgeConnecting(a5, a1) &&
                        !G.hasEdgeConnecting(a5, a2) &&
                        !G.hasEdgeConnecting(a5, a3) &&
                        !G.hasEdgeConnecting(a5, a4) &&
                        !G.hasEdgeConnecting(a4, a1) &&
                        !G.hasEdgeConnecting(a4, a2) &&
                        !G.hasEdgeConnecting(a3, a2) &&
                        !G.hasEdgeConnecting(a3, a1) &&
                        G.hasEdgeConnecting(a5, a5), true);
    }
}
