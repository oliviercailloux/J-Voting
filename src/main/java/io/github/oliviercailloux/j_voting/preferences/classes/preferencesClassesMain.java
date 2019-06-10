package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class preferencesClassesMain {
    
    
    // Main to test and debug, will be removed after developpement
    public static void main(String[] args) {
        
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        
        Set<Alternative> A = Set.of(a1, a2);
        Set<Alternative> B = Set.of(a3, a4);
        Set<Alternative> C = Set.of(a5);
        
        ArrayList<Set<Alternative>> listTest = new ArrayList();
        listTest.add(A);
        listTest.add(B);
        listTest.add(C);
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        
        
        MutablePreferenceImpl pref = MutablePreferenceImpl.of(setTest, Voter.createVoter(1));
        System.out.println(pref.asGraph());
        
    }
}
