package io.github.oliviercailloux.j_voting.preferences;

import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;
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
    
        Set<Alternative> A = new HashSet<>();
        A.add(a1);
        A.add(a2);
        Set<Alternative> B = new HashSet<>();
        B.add(a3);
        B.add(a4);
        Set<Alternative> C = new HashSet<>();
        C.add(a5);
    
        ArrayList<Set<Alternative>> listTest = new ArrayList();
        listTest.add(A);
        listTest.add(B);
        listTest.add(C);
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
    
    
        MutablePreferenceImpl pref = MutablePreferenceImpl.of(setTest, Voter.createVoter(1));
        MutableGraph G = pref.asGraph();
        assertEquals(G.nodes().containsAll(Set.of(a1, a2, a3, a4, a5)), true);
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
