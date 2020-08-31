package io.github.oliviercailloux.j_voting.preferences.classes;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.ImmutableLinearPreference;

public class ImmutableLinearPreferenceImpl implements ImmutableLinearPreference {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableLinearPreferenceImpl.class);
	private final Voter voter;

	/**
	 * We want both fast access to rank (not provided by ImmutableList, whose
	 * indexOf operation is slow) and random access (not provided by ImmutableSet).
	 * See also https://github.com/google/guava/issues/1786 and
	 * https://github.com/google/guava/issues/13.
	 */
	private final ImmutableBiMap<Alternative, Integer> ranks;
	private final ImmutableGraph<Alternative> graph;

	/**
	 * Best first
	 */
	public static ImmutableLinearPreference given(List<Alternative> list) {
		return new ImmutableLinearPreferenceImpl(Voter.ZERO, list);
	}

	/**
	 * Best first
	 */
	public static ImmutableLinearPreference given(Voter voter, List<Alternative> list) {
		return new ImmutableLinearPreferenceImpl(voter, list);
	}

	private ImmutableLinearPreferenceImpl(Voter voter, List<Alternative> list) {
		this.voter = checkNotNull(voter);

		final ImmutableBiMap.Builder<Alternative, Integer> builder = ImmutableBiMap.builder();
		int rank = 1;
		for (Alternative alternative : list) {
			builder.put(alternative, rank);
			++rank;
		}
		ranks = builder.build();

		this.graph = asGraph(list);
	}

	private ImmutableGraph<Alternative> asGraph(List<Alternative> list) {
		final ImmutableGraph.Builder<Alternative> builder = GraphBuilder.directed().allowsSelfLoops(true).immutable();
		final Set<Alternative> alternativesSoFar = new LinkedHashSet<>();
		for (Alternative current : list) {
			final boolean added = alternativesSoFar.add(current);
			checkArgument(added, "Duplicate: " + current + ".");
			for (Alternative previous : alternativesSoFar) {
				builder.putEdge(previous, current);
			}
		}
		return builder.build();
	}

	@Override
	public Voter getVoter() {
		return this.voter;
	}

	@Override
	public ImmutableSet<Alternative> getAlternatives() {
		return ranks.keySet();
	}

	@Override
	public ImmutableList<Alternative> asList() {
		return ranks.keySet().asList();
	}

	@Override
	public int getRank(Alternative a) {
		final Integer rank = ranks.get(a);
		if (rank == null) {
			throw new NoSuchElementException(a.toString());
		}
		return rank;
	}

	@Override
	public ImmutableSet<Alternative> getAlternatives(int rank) {
		return ImmutableSet.of(ranks.inverse().get(rank));
	}

	@Override
	public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
		return ranks.keySet().asList().stream().map(ImmutableSet::of).collect(ImmutableList.toImmutableList());
	}

	@Override
	public ImmutableGraph<Alternative> asGraph() {
		return graph;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof ImmutableLinearPreferenceImpl)) {
			return false;
		}
		ImmutableLinearPreferenceImpl other = (ImmutableLinearPreferenceImpl) o2;
		return Objects.equals(voter, other.voter) && Objects.equals(ranks, other.ranks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voter, ranks);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Voter", voter).add("Ranks", ranks).toString();
	}
}
