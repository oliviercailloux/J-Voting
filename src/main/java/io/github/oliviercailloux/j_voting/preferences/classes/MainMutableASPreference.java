package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

public class MainMutableASPreference {
	public static void main(String[] args) {
		Alternative a1 = Alternative.withId(1);
		// Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);
		// Alternative a4 = Alternative.withId(4);
		// Alternative a5 = Alternative.withId(5);
		// Alternative a6 = Alternative.withId(6);
		Set<Alternative> A = Sets.newHashSet(a1);
		Set<Alternative> B = Sets.newHashSet(a3);
		ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
		Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
		setTest.add(listTest);
		MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
				.given(MutableAntiSymmetricPreferenceImpl.preferenceGraphMaker(setTest), Voter.createVoter(1));

		/*
		 * MutableGraph<Alternative> graphTest = pref.asGraph(); graphTest.addNode(a6);
		 * graphTest.putEdge(a5, a6);
		 * 
		 * Graph<Alternative> g = Graphs.transitiveClosure(graphTest);
		 * 
		 * System.out.println(g);
		 */
		// pref.addAlternative(a6);
		// pref.putEdge(a5, Alternative.withId(7));
		System.out.println(pref);
	}
}
