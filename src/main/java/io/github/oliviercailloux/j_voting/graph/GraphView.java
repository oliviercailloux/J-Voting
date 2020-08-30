package io.github.oliviercailloux.j_voting.graph;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;

/**
 * The sets accessible via this delegate are not currently editable
 * (google/guava#3034), but this is not (currently) guaranteed by contract.
 */
public class GraphView<N> extends ForwardingGraph<N> {
	public static <N> Graph<N> decorate(Graph<N> delegate) {
		return new GraphView<>(checkNotNull(delegate));
	}

	private GraphView(Graph<N> delegate) {
		this.delegate = delegate;
	}

	private Graph<N> delegate;

	@Override
	protected Graph<N> delegate() {
		return delegate;
	}

	@Override
	public Set<N> nodes() {
		return Collections.unmodifiableSet(super.nodes());
	}

	@Override
	public Set<N> adjacentNodes(N node) {
		return Collections.unmodifiableSet(super.adjacentNodes(node));
	}

	@Override
	public Set<N> predecessors(N node) {
		return Collections.unmodifiableSet(super.predecessors(node));
	}

	@Override
	public Set<N> successors(N node) {
		return Collections.unmodifiableSet(super.successors(node));
	}

	@Override
	public Set<EndpointPair<N>> edges() {
		return Collections.unmodifiableSet(super.edges());
	}

	@Override
	public Set<EndpointPair<N>> incidentEdges(N node) {
		return Collections.unmodifiableSet(super.incidentEdges(node));
	}
}