package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.ImmutableLinearPreference;
import io.github.oliviercailloux.j_voting.preferences.Preference;

public class LinearPreferenceImpl extends CompletePreferenceImpl implements ImmutableLinearPreference {

	ImmutableList<Alternative> list;
	private static final Logger LOGGER = LoggerFactory.getLogger(LinearPreferenceImpl.class.getName());

	/**
	 * 
	 * @param voter            <code> not null </code>
	 * @param listAlternatives <code> not null </code>
	 * @return new LinearPreference
	 * @throws EmptySetException
	 * @throws DuplicateValueException
	 */
	public static ImmutableLinearPreference asLinearPreference(Voter voter, List<Alternative> listAlternatives)
			throws EmptySetException, DuplicateValueException {
		LOGGER.debug("LinearPreferenceImpl Factory with list of Alternatives");
		Preconditions.checkNotNull(voter);
		Preconditions.checkNotNull(listAlternatives);
		List<Set<Alternative>> equivalenceClasses = Lists.newArrayList();
		for (Alternative alternative : listAlternatives) {
			equivalenceClasses.add(ImmutableSet.of(alternative));
		}
		return new LinearPreferenceImpl(voter, equivalenceClasses);
	}

	/**
	 * 
	 * @param voter              <code> not null </code>
	 * @param equivalenceClasses <code> not null </code>
	 * @throws EmptySetException
	 * @throws DuplicateValueException
	 */
	private LinearPreferenceImpl(Voter voter, List<Set<Alternative>> equivalenceClasses)
			throws EmptySetException, DuplicateValueException {
		super(voter, equivalenceClasses);
		List<Alternative> tmpList = Lists.newArrayList();
		for (Set<Alternative> equivalenceClass : equivalenceClasses) {
			Alternative alternative = Iterables.getOnlyElement(equivalenceClass);
			tmpList.add(alternative);
		}
		this.list = ImmutableList.copyOf(tmpList);
	}

	@Override
	public ImmutableList<Alternative> asList() {
		return this.list;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.asGraph());
	}

	@Override
	public boolean equals(Object o2) {
		if (this == o2) {
			return true;
		}
		if (!super.equals(o2)) {
			return false;
		}
		if (!(o2 instanceof Preference)) {
			return false;
		}
		Preference other = (LinearPreferenceImpl) o2;
		return this.asGraph().equals(other.asGraph());
	}
}
