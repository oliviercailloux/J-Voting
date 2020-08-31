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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.ImmutableCompletePreference;

/**
 * private ImmutableMap<Alternative, Integer>
 * asRanks(@SuppressWarnings("hiding") Graph<Alternative> graph) {
 *
 * From
 * https://github.com/jgrapht/jgrapht/blob/master/jgrapht-core/src/main/java/org/jgrapht/alg/connectivity/AbstractStrongConnectivityInspector.java:
 * find strongly connected components; map each alternative to its component;
 * then for each edge in the original graph, link the two components in the
 * output graph. Compute the transitive closure of the resulting graph. The rank
 * of each component is given by the number of components it reaches. Should
 * also check that the resulting component graph is linear.
 */
public class ImmutableCompletePreferenceImpl implements ImmutableCompletePreference {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableCompletePreferenceImpl.class);
	private final Voter voter;
	private final ImmutableList<ImmutableSet<Alternative>> equivalenceClasses;
	private final ImmutableGraph<Alternative> graph;

	private final ImmutableMap<Alternative, Integer> ranks;

	private final ImmutableSet<Alternative> alternatives;

	/**
	 *
	 * @param equivalenceClasses <code> not null </code> the best equivalence class
	 *                           must be in first position. An alternative must be
	 *                           unique
	 */
	public static ImmutableCompletePreference given(Voter voter, List<? extends Set<Alternative>> equivalenceClasses) {
		checkNotNull(voter);
		checkNotNull(equivalenceClasses);
		return new ImmutableCompletePreferenceImpl(voter, equivalenceClasses);
	}

	protected ImmutableCompletePreferenceImpl(Voter voter, List<? extends Set<Alternative>> equivalenceClasses) {
		this.voter = checkNotNull(voter);
		this.equivalenceClasses = equivalenceClasses.stream().map(ImmutableSet::copyOf)
				.collect(ImmutableList.toImmutableList());
		this.graph = asGraph(equivalenceClasses);
		this.ranks = asRanks(equivalenceClasses);
		this.alternatives = ImmutableSet.copyOf(graph.nodes());
	}

	private ImmutableGraph<Alternative> asGraph(
			@SuppressWarnings("hiding") List<? extends Set<Alternative>> equivalenceClasses) {
		final ImmutableGraph.Builder<Alternative> builder = GraphBuilder.directed().allowsSelfLoops(true).immutable();
		final Set<Alternative> alternativesSoFar = new LinkedHashSet<>();
		for (Set<Alternative> equivalenceClass : equivalenceClasses) {
			checkArgument(!equivalenceClass.isEmpty());
			for (Alternative current : equivalenceClass) {
				checkArgument(!alternativesSoFar.contains(current), "Duplicate: " + current + ".");

				for (Alternative previous : alternativesSoFar) {
					builder.putEdge(previous, current);
				}
				for (Alternative equivalent : equivalenceClass) {
					builder.putEdge(equivalent, current);
				}
			}
			alternativesSoFar.addAll(equivalenceClass);
		}
		return builder.build();
	}

	private ImmutableMap<Alternative, Integer> asRanks(
			@SuppressWarnings("hiding") List<? extends Set<Alternative>> equivalenceClasses) {
		int rank = 1;
		final ImmutableMap.Builder<Alternative, Integer> builder = ImmutableMap.builder();
		for (Set<Alternative> equivalenceClass : equivalenceClasses) {
			for (Alternative alternative : equivalenceClass) {
				builder.put(alternative, rank);
			}
			++rank;
		}
		return builder.build();
	}

	@Override
	public Voter getVoter() {
		return this.voter;
	}

	@Override
	public ImmutableSet<Alternative> getAlternatives() {
		return this.alternatives;
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
		return equivalenceClasses.get(rank - 1);
	}

	@Override
	public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
		return equivalenceClasses;
	}

	@Override
	public ImmutableGraph<Alternative> asGraph() {
		return graph;
	}

	public boolean isAntiSymmetric() {
		return (equivalenceClasses.size() == alternatives.size());
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof ImmutableCompletePreferenceImpl)) {
			return false;
		}
		ImmutableCompletePreferenceImpl other = (ImmutableCompletePreferenceImpl) o2;
		return Objects.equals(voter, other.voter) && Objects.equals(equivalenceClasses, other.equivalenceClasses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voter, equivalenceClasses);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Voter", voter).add("Equivalence classes", equivalenceClasses)
				.toString();
	}
}
