package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.Alternative.a1;
import static io.github.oliviercailloux.j_voting.Alternative.a2;
import static io.github.oliviercailloux.j_voting.Alternative.a4;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

class ImmutableAntiSymmetricPreferenceImplTest {

	private static Voter v1 = Voter.withId(1);

	@Test
	void asImmutableAntisymmetricPreferenceException() {
		MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph.putEdge(a1, a1);
		graph.putEdge(a4, a4);
		graph.putEdge(a1, a4);
		graph.putEdge(a4, a1);
		assertThrows(IllegalArgumentException.class,
				() -> ImmutableAntiSymmetricPreferenceImpl.asImmutableAntiSymmetricPreference(v1, graph));
	}

	@Test
	void asImmutableAntisymmetricPreferenceReflexiveException() {
		MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph.putEdge(a1, a4);
		graph.putEdge(a4, a2);
		graph.putEdge(a2, a1);
		assertThrows(IllegalArgumentException.class,
				() -> ImmutableAntiSymmetricPreferenceImpl.asImmutableAntiSymmetricPreference(v1, graph));
	}

	@Test
	void asImmutableAntisymmetricPreferenceLoopTest() {
		MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph.putEdge(a1, a1);
		assertDoesNotThrow(() -> ImmutableAntiSymmetricPreferenceImpl.asImmutableAntiSymmetricPreference(v1, graph));
		graph.putEdge(a1, a2);
		graph.putEdge(a2, a1);
		assertThrows(IllegalArgumentException.class, () -> ImmutableAntiSymmetricPreferenceImpl
				.asImmutableAntiSymmetricPreference(Voter.withId(2), graph));
	}
}
