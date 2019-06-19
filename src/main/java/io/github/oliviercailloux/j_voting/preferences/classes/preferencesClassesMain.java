package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

public class preferencesClassesMain {

	// Main to test and debug, will be removed after development
	public static void main(String[] args) {
		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);
		Alternative a4 = Alternative.withId(4);
		Alternative a5 = Alternative.withId(5);
		Alternative a6 = Alternative.withId(6);
		Set<Alternative> A = Sets.newHashSet(a1, a2);
		Set<Alternative> B = Sets.newHashSet(a3, a4);
		Set<Alternative> C = Sets.newHashSet(a5);
		ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B, C);
		Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
		setTest.add(listTest);
		MutablePreferenceImpl pref = MutablePreferenceImpl.given(MutablePreferenceImpl.preferenceGraphMaker(setTest),
				Voter.createVoter(1));

		/*
		 * MutableGraph<Alternative> graphTest = pref.asGraph(); graphTest.addNode(a6);
		 * graphTest.putEdge(a5, a6);
		 * 
		 * Graph<Alternative> g = Graphs.transitiveClosure(graphTest);
		 * 
		 * System.out.println(g);
		 */
		pref.addAlternative(a6);
		pref.putEdge(a5, Alternative.withId(7));
		pref.addEquivalence(a6, a5);
		System.out.println(pref);
	}
}
