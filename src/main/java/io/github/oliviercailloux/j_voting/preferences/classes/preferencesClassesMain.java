package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.collect.Lists;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import org.eclipse.collections.impl.factory.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class preferencesClassesMain {
    
    // Main to test and debug, will be removed after development
    public static void main(String[] args) {
        
        
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Alternative a6 = Alternative.withId(6);
        Set<Alternative> A = Sets.mutable.of(a1, a2);
        Set<Alternative> B = Sets.mutable.of(a3, a4);
        Set<Alternative> C = Sets.mutable.of(a5);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B, C);
        Set<List<Set<Alternative>>> setTest = Sets.mutable.of(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl
                        .of(setTest, Voter.createVoter(1));
        
        /*
        MutableGraph<Alternative> graphTest = pref.asGraph();
        graphTest.addNode(a6);
        graphTest.putEdge(a5, a6);
        
        Graph<Alternative> g = Graphs.transitiveClosure(graphTest);
        
        System.out.println(g);
        */
        pref.addAlternative(a6);
        pref.addStrictPreference(a5, Alternative.withId(7));
        pref.addExAequo(a6, a5);
    
        pref.addAlternative(a6);
        pref.addAlternative(a6);
        pref.addAlternative(a6);
        System.out.println(pref);
        
        
        
    }
}
