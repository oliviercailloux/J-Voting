package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.ImmutableCompletePreference;

public class ImmutableCompletePreferenceImpl implements ImmutableCompletePreference {

	private ImmutableList<ImmutableSet<Alternative>> equivalenceClasses;
	private Voter voter;
	private ImmutableGraph<Alternative> graph;
	private ImmutableSet<Alternative> alternatives;
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableCompletePreferenceImpl.class.getName());

	/**
	 * 
	 * @param equivalenceClasses <code> not null </code> the best equivalence class
	 *                           must be in first position. An alternative must be
	 *                           unique
	 * @param voter              <code> not null </code>
	 * @return new CompletePreference
	 * @throws DuplicateValueException if an Alternative is duplicate
	 * @throws EmptySetException       if a Set is empty
	 */
	public static ImmutableCompletePreference asCompletePreference(Voter voter,
			List<? extends Set<Alternative>> equivalenceClasses) throws DuplicateValueException, EmptySetException {
		LOGGER.debug("Factory CompletePreferenceImpl");
		checkNotNull(equivalenceClasses);
		checkNotNull(voter);
		return new ImmutableCompletePreferenceImpl(voter, equivalenceClasses);
	}

	/**
	 * 
	 * @param equivalenceClasses <code> not null </code>
	 * @param voter              <code> not null </code>
	 * @throws EmptySetException       if a Set is empty
	 * @throws DuplicateValueException if an Alternative is duplicate
	 */
	protected ImmutableCompletePreferenceImpl(Voter voter, List<? extends Set<Alternative>> equivalenceClasses)
			throws EmptySetException, DuplicateValueException {
		LOGGER.debug("Constructor CompletePreferenceImpl");
		this.voter = voter;
		List<ImmutableSet<Alternative>> listImmutableSets = Lists.newArrayList();
		for (Set<Alternative> set : equivalenceClasses) {
			listImmutableSets.add(ImmutableSet.copyOf(set));
		}
		this.equivalenceClasses = ImmutableList.copyOf(listImmutableSets);
		this.graph = createGraph(equivalenceClasses);
		this.alternatives = ImmutableSet.copyOf(this.graph.nodes());
	}

	/**
	 * Return the graph associated to the preference.
	 *
	 * @param equivalenceClasses a list of set of alternative
	 * @throws EmptySetException       if a Set is empty
	 * @throws DuplicateValueException if an Alternative is duplicate
	 */
	private ImmutableGraph<Alternative> createGraph(List<? extends Set<Alternative>> equivalenceClasses)
			throws EmptySetException, DuplicateValueException {
		List<Alternative> listAlternatives = Lists.newArrayList();
		MutableGraph<Alternative> newGraph = GraphBuilder.directed().allowsSelfLoops(true).build();
		Alternative lastSetLinker = null;
		for (Set<Alternative> equivalenceClass : equivalenceClasses) {
			if (equivalenceClass.isEmpty())
				throw new EmptySetException("A Set can't be empty");
			Alternative rememberAlternative = null;
			for (Alternative alternative : equivalenceClass) {
				if (listAlternatives.contains(alternative))
					throw new DuplicateValueException("you can't duplicate Alternatives");
				listAlternatives.add(alternative);
				if (lastSetLinker != null) {
					newGraph.putEdge(lastSetLinker, alternative);
					lastSetLinker = null;
				}
				newGraph.putEdge(alternative, alternative);
				if (!Objects.isNull(rememberAlternative)) {
					newGraph.putEdge(rememberAlternative, alternative);
					newGraph.putEdge(alternative, rememberAlternative);
				}
				rememberAlternative = alternative;
			}
			lastSetLinker = rememberAlternative;
		}
		return ImmutableGraph.copyOf(Graphs.transitiveClosure(newGraph));
	}

	@Override
	public ImmutableSet<Alternative> getAlternatives() {
		return this.alternatives;
	}

	@Override
	public Voter getVoter() {
		return this.voter;
	}

	@Override
	public int getRank(Alternative a) {
		checkNotNull(a);
		for (ImmutableSet<Alternative> equivalenceClass : equivalenceClasses) {
			if (equivalenceClass.contains(a))
				return equivalenceClasses.indexOf(equivalenceClass) + 1;
		}
		throw new NoSuchElementException("Alternative not found");
	}

	@Override
	public ImmutableSet<Alternative> getAlternatives(int rank) {
		return ImmutableSet.copyOf(equivalenceClasses.get(rank - 1));
	}

	@Override
	public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
		return equivalenceClasses;
	}

	@Override
	public ImmutableGraph<Alternative> asGraph() {
		return graph;
	}

	/**
	 *
	 * @return true if the Preference is Strict (without several alternatives having
	 *         the same rank)
	 */
	public boolean isStrict() {
		return (this.equivalenceClasses.size() == this.alternatives.size());
	}

	/**
	 * 
	 * @return the StrictPreference built from the preference if the preference is
	 *         strict. If the preference is not strict it throws an
	 *         IllegalArgumentException.
	 */
	public OldLinearPreferenceImpl toStrictPreference() {
		if (!isStrict()) {
			throw new IllegalArgumentException("The preference is not strict.");
		}
		return OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(this.alternatives.asList());
	}

	@Override
	public int hashCode() {
		return Objects.hash(equivalenceClasses, graph, voter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ImmutableCompletePreferenceImpl)) {
			return false;
		}
		ImmutableCompletePreferenceImpl other = (ImmutableCompletePreferenceImpl) obj;
		return Objects.equals(equivalenceClasses, other.equivalenceClasses) && Objects.equals(graph, other.graph)
				&& Objects.equals(voter, other.voter);
	}
}
