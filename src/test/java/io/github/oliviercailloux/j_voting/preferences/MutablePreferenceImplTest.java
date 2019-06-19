package io.github.oliviercailloux.j_voting.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.collections.impl.factory.Sets;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;

class MutablePreferenceImplTest {

	@Test
	void of() {
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
		MutableGraph g = pref.asGraph();
		assertEquals(g.nodes().containsAll(Sets.mutable.of(a1, a2, a3, a4, a5)), true);
		assertEquals(
				g.hasEdgeConnecting(a1, a1) && g.hasEdgeConnecting(a1, a2) && g.hasEdgeConnecting(a1, a3)
						&& g.hasEdgeConnecting(a1, a5) && g.hasEdgeConnecting(a2, a1) && g.hasEdgeConnecting(a2, a2)
						&& g.hasEdgeConnecting(a2, a3) && g.hasEdgeConnecting(a2, a4) && g.hasEdgeConnecting(a2, a5)
						&& g.hasEdgeConnecting(a3, a3) && g.hasEdgeConnecting(a3, a4) && g.hasEdgeConnecting(a3, a5)
						&& g.hasEdgeConnecting(a4, a3) && g.hasEdgeConnecting(a4, a4) && g.hasEdgeConnecting(a4, a5)
						&& !g.hasEdgeConnecting(a5, a1) && !g.hasEdgeConnecting(a5, a2) && !g.hasEdgeConnecting(a5, a3)
						&& !g.hasEdgeConnecting(a5, a4) && !g.hasEdgeConnecting(a4, a1) && !g.hasEdgeConnecting(a4, a2)
						&& !g.hasEdgeConnecting(a3, a2) && !g.hasEdgeConnecting(a3, a1) && g.hasEdgeConnecting(a5, a5),
				true);
	}
}
