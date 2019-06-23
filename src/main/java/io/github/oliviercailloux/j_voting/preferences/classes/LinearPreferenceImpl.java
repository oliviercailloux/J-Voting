package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

public class LinearPreferenceImpl extends CompletePreferenceImpl
                implements LinearPreference {

    ImmutableList<Alternative> list;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(LinearPreferenceImpl.class.getName());

    /**
     * 
     * @param voter              <code> not null </code>
     * @param equivalenceClasses <code> not null </code>
     * @return new LinearPreference
     * @throws EmptySetException
     * @throws DuplicateValueException
     */
    public static LinearPreference asLinearPreference(Voter voter,
                    List<Set<Alternative>> equivalenceClasses)
                    throws EmptySetException, DuplicateValueException {
        LOGGER.debug("LinearPreferenceImpl Factory");
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(equivalenceClasses);
        for (Set<Alternative> set : equivalenceClasses) {
            if (set.size() > 1) {
                throw new IllegalArgumentException(
                                "Alternatives can't be equals");
            }
        }
        return new LinearPreferenceImpl(voter, equivalenceClasses);
    }

    /**
     * 
     * @param voter            <code> not null </code>
     * @param listAlternatives <code> not null </code>
     * @return new LinearPreference
     * @throws EmptySetException
     * @throws DuplicateValueException
     */
    public static LinearPreference listAlternativesToLinearPreference(
                    Voter voter, List<Alternative> listAlternatives)
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
    private LinearPreferenceImpl(Voter voter,
                    List<Set<Alternative>> equivalenceClasses)
                    throws EmptySetException, DuplicateValueException {
        super(voter, equivalenceClasses);
        List<Alternative> tmpList = Lists.newArrayList();
        for (Set<Alternative> set : equivalenceClasses) {
            for (Alternative alternative : set) {
                tmpList.add(alternative);
            }
        }
        this.list = ImmutableList.copyOf(tmpList);
    }

    @Override
    public ImmutableList<Alternative> asList() {
        return this.list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(list);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof LinearPreferenceImpl)) {
            return false;
        }
        LinearPreferenceImpl other = (LinearPreferenceImpl) obj;
        return Objects.equals(list, other.list);
    }
}
