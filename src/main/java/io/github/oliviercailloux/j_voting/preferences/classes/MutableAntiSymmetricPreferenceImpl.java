package io.github.oliviercailloux.j_voting.preferences.classes;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.graph.ForwardingMutableGraph;
import io.github.oliviercailloux.j_voting.graph.GraphView;
import io.github.oliviercailloux.j_voting.preferences.MutableAntiSymmetricPreference;

/**
 * This structure keeps the mutable graph as in the original. It maintains a
 * transitive closure iff {@link #asGraph()} has been called iff it guarantees
 * that the transitive closure of its mutable graph minus its reflective part is
 * acyclic.
 */
public class MutableAntiSymmetricPreferenceImpl implements MutableAntiSymmetricPreference {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableAntiSymmetricPreferenceImpl.class);

	public static MutableAntiSymmetricPreference empty() {
		return new MutableAntiSymmetricPreferenceImpl(Voter.ZERO, GraphBuilder.directed().build());
	}

	public static MutableAntiSymmetricPreference about(Voter voter) {
		return new MutableAntiSymmetricPreferenceImpl(voter, GraphBuilder.directed().build());
	}

	public static MutableAntiSymmetricPreference given(Graph<Alternative> graph) {
		return new MutableAntiSymmetricPreferenceImpl(Voter.ZERO, graph);
	}

	public static MutableAntiSymmetricPreference given(Voter voter, Graph<Alternative> graph) {
		return new MutableAntiSymmetricPreferenceImpl(voter, graph);
	}

	private static class AlternativesView extends ForwardingSet<Alternative> {
		private final MutableAntiSymmetricPreferenceImpl delegate;

		private AlternativesView(MutableAntiSymmetricPreferenceImpl delegate) {
			this.delegate = delegate;
		}

		@Override
		protected Set<Alternative> delegate() {
			return delegate.originalGraph.nodes();
		}

		@Override
		public boolean add(Alternative a) {
			return delegate.addAlternative(a);
		}

		@Override
		public boolean addAll(Collection<? extends Alternative> c) {
			return standardAddAll(c);
		}

		@Override
		public boolean remove(Object o) {
			if (!(o instanceof Alternative)) {
				return false;
			}

			final Alternative toRemove = (Alternative) o;
			return delegate.removeAlternative(toRemove);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return standardRemoveAll(c);
		}

		@Override
		public void clear() {
			delegate.clear();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return standardRetainAll(c);
		}
	}

	private static class MutableGraphView extends ForwardingMutableGraph<Alternative> {

		private final MutableAntiSymmetricPreferenceImpl delegate;
		private final AlternativesView nodesView;

		private MutableGraphView(MutableAntiSymmetricPreferenceImpl delegate) {
			super(delegate.originalGraph);
			this.delegate = delegate;
			nodesView = new AlternativesView(delegate);
		}

		@Override
		public Set<Alternative> nodes() {
			return nodesView;
		}

		@Override
		public Set<Alternative> adjacentNodes(Alternative node) {
			return Collections.unmodifiableSet(super.adjacentNodes(node));
		}

		@Override
		public Set<Alternative> predecessors(Alternative node) {
			return Collections.unmodifiableSet(super.predecessors(node));
		}

		@Override
		public Set<Alternative> successors(Alternative node) {
			return Collections.unmodifiableSet(super.successors(node));
		}

		@Override
		public Set<EndpointPair<Alternative>> edges() {
			return Collections.unmodifiableSet(super.edges());
		}

		@Override
		public Set<EndpointPair<Alternative>> incidentEdges(Alternative node) {
			return Collections.unmodifiableSet(super.incidentEdges(node));
		}

		@Override
		public boolean addNode(Alternative node) {
			return delegate.addAlternative(node);
		}

		@Override
		public boolean removeNode(Alternative node) {
			return delegate.removeAlternative(node);
		}

		@Override
		public boolean putEdge(Alternative nodeU, Alternative nodeV) {
			return delegate.addStrictPreference(nodeU, nodeV);
		}

		@Override
		public boolean removeEdge(Alternative nodeU, Alternative nodeV) {
			return delegate.removeEdge(nodeU, nodeV);
		}

		@Override
		public boolean removeEdge(EndpointPair<Alternative> endpoints) {
			return delegate.removeEdge(endpoints.source(), endpoints.target());
		}

	}

	private Voter voter;
	private MutableGraph<Alternative> originalGraph;
	private MutableGraph<Alternative> originalGraphView;
	/**
	 * <code>null</code> as long as not initialized.
	 */
	private MutableGraph<Alternative> transitiveGraph;
	/**
	 * <code>null</code> iff {@link #transitiveGraph} is <code>null</code>.
	 */
	private Graph<Alternative> transitiveGraphView;

	/**
	 * Performs no check for acyclicity, but checks irreflexivity.
	 */
	private MutableAntiSymmetricPreferenceImpl(Voter voter, Graph<Alternative> graph) {
		this.voter = checkNotNull(voter);

		/**
		 * This builder will disallow reflexive edges, thereby guaranteeing
		 * irreflexivity.
		 */
		originalGraph = GraphBuilder.directed().nodeOrder(graph.nodeOrder()).expectedNodeCount(graph.nodes().size())
				.build();
		for (Alternative node : graph.nodes()) {
			originalGraph.addNode(node);
		}
		for (EndpointPair<Alternative> edge : graph.edges()) {
			originalGraph.putEdge(edge.nodeU(), edge.nodeV());
		}

		originalGraphView = new MutableGraphView(this);
	}

	@Override
	public Voter getVoter() {
		return voter;
	}

	@Override
	public Set<Alternative> getAlternatives() {
		return originalGraphView.nodes();
	}

	@Override
	public boolean addAlternative(Alternative alternative) {
		checkNotNull(alternative);
		LOGGER.debug("Added {}.", alternative);
		final boolean added = originalGraph.addNode(alternative);
		if (transitiveGraph != null) {
			final boolean addedSecond = transitiveGraph.putEdge(alternative, alternative);
			LOGGER.debug("Added {} loop to transitive.", alternative);
			verify(added == addedSecond);
		}
		return added;
	}

	boolean removeAlternative(Alternative alternative) {
		checkNotNull(alternative);
		final boolean removed = originalGraph.removeNode(alternative);
		if (transitiveGraph != null) {
			final boolean removedSecond = transitiveGraph.removeNode(alternative);
			verify(removed == removedSecond);
			refreshClosure();
		}
		return removed;
	}

	@Override
	public Graph<Alternative> asGraph() {
		if (transitiveGraph == null) {
			final Graph<Alternative> transitiveClosure = Graphs.transitiveClosure(originalGraph);
			final Set<Alternative> alternatives = transitiveClosure.nodes();
			for (Alternative alternative : alternatives) {
				checkState(Sets
						.intersection(transitiveClosure.predecessors(alternative),
								transitiveClosure.successors(alternative))
						.immutableCopy().equals(ImmutableSet.of(alternative)));
			}
			transitiveGraph = Graphs.copyOf(transitiveClosure);
			transitiveGraphView = GraphView.decorate(transitiveGraph);
		}
		verify(transitiveGraphView != null);

		return transitiveGraphView;
	}

	@Override
	public MutableGraph<Alternative> asMutableGraph() {
		return originalGraphView;
	}

	@Override
	public boolean addStrictPreference(Alternative a1, Alternative a2) {
		checkNotNull(a1);
		checkNotNull(a2);
		checkArgument(!originalGraph.hasEdgeConnecting(a2, a1));

		final boolean added = originalGraph.putEdge(a1, a2);
		if (!added) {
			return false;
		}

		if (transitiveGraph != null) {
			transitiveGraph.putEdge(a1, a1);
			transitiveGraph.putEdge(a2, a2);

			for (Alternative predecessor : transitiveGraph.predecessors(a1)) {
				for (Alternative successor : transitiveGraph.successors(a2)) {
					transitiveGraph.putEdge(predecessor, successor);
				}
			}

			verify(transitiveGraph.hasEdgeConnecting(a1, a2));
			if (transitiveGraph.hasEdgeConnecting(a2, a1)) {
				refreshClosure();
				throw new IllegalArgumentException(String.format("Adding the edge (%s, %s) creates a cycle.", a1, a2));
			}
		}

		return true;
	}

	boolean removeEdge(Alternative a1, Alternative a2) {
		final boolean removed = originalGraph.removeEdge(a1, a2);
		if (removed && transitiveGraph != null) {
			/**
			 * Better re-compute everything from scratch; otherwise, we have to, for each
			 * predecessor p of a1, look at each successor s of a2 and remove (p, s) if not
			 * reachable in original; and I think this might take about (m/2)² × m.
			 */

			refreshClosure();
		}
		return removed;
	}

	private void refreshClosure() {
		checkState(transitiveGraph != null);
		final Set<EndpointPair<Alternative>> oldEdges = ImmutableSet.copyOf(transitiveGraph.edges());
		for (EndpointPair<Alternative> endpointPair : oldEdges) {
			transitiveGraph.removeEdge(endpointPair);
		}

		final Set<EndpointPair<Alternative>> newEdges = Graphs.transitiveClosure(originalGraph).edges();
		for (EndpointPair<Alternative> endpointPair : newEdges) {
			transitiveGraph.putEdge(endpointPair);
		}
	}

	void clear() {
		{
			final Set<EndpointPair<Alternative>> edges = originalGraph.edges();
			for (EndpointPair<Alternative> endpointPair : edges) {
				originalGraph.removeEdge(endpointPair);
			}
		}
		{
			final Set<EndpointPair<Alternative>> edges = transitiveGraph.edges();
			for (EndpointPair<Alternative> endpointPair : edges) {
				transitiveGraph.removeEdge(endpointPair);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MutableAntiSymmetricPreferenceImpl)) {
			return false;
		}
		MutableAntiSymmetricPreferenceImpl other = (MutableAntiSymmetricPreferenceImpl) obj;
		return Objects.equals(voter, other.voter) && Objects.equals(originalGraph, other.originalGraph);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voter, originalGraph);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("voter", voter).add("graph", originalGraph).toString();
	}
}
