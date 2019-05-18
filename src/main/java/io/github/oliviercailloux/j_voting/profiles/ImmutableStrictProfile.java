package io.github.oliviercailloux.j_voting.profiles;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;

/**
 * This class is immutable. Represents a Strict Complete Profile.
 */
public class ImmutableStrictProfile extends ImmutableStrictProfileI
                implements StrictProfile {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ImmutableStrictProfile.class.getName());

    public ImmutableStrictProfile(Map<Voter, ? extends CompletePreferenceImpl> map) {
        super(checkCompleteMap(map));
    }

    @Override
    public int getNbAlternatives() {
        LOGGER.debug("getNbAlternatives:");
        return getAlternatives().size();
    }

    @Override
    public Set<Alternative> getAlternatives() {
        LOGGER.debug("getAlternatives :");
        CompletePreferenceImpl p = votes.values().iterator().next();
        return CompletePreferenceImpl.toAlternativeSet(p.getPreferencesNonStrict());
    }

    /**
     * Get a List of each ith Alternative of each Voter in the profile
     * 
     * @param i not <code> null</code> the rank of the Alternatives to get
     * @return a List of Alternatives
     */
    @Override
    public List<Alternative> getIthAlternatives(int i) {
        LOGGER.debug("getIthAlternatives :");
        Preconditions.checkNotNull(i);
        NavigableSet<Voter> voters = getAllVoters();
        List<Alternative> listIthAlternatives = new ArrayList<>();
        for (Voter v : voters) {
            listIthAlternatives.add(getPreference(v).getAlternative(i));
        }
        return listIthAlternatives;
    }

    /**
     * Get a List of each ith Alternative of each unique Preference in the
     * profile
     * 
     * @param i not <code> null</code> the rank of the Alternatives to get
     * @return a List of Alternatives
     */
    @Override
    public List<Alternative> getIthAlternativesOfUniquePreferences(int i) {
        LOGGER.debug("getIthAlternativesOfUniquePreferences :");
        Preconditions.checkNotNull(i);
        List<Alternative> listIthAlternatives = new ArrayList<>();
        for (CompletePreferenceImpl p : getUniquePreferences()) {
            listIthAlternatives.add(p.getAlternative(i));
        }
        return listIthAlternatives;
    }

    @Override
    public void writeToSOC(OutputStream output) throws IOException {
        LOGGER.debug("writeToSOC :");
        Preconditions.checkNotNull(output);
        try (Writer writer = new BufferedWriter(
                        new OutputStreamWriter(output))) {
            StringBuilder soc = new StringBuilder();
            soc.append(getNbAlternatives() + "\n");
            for (Alternative alter : getAlternatives()) {
                soc.append(alter.getId() + "\n");
            }
            soc.append(getNbVoters() + "," + getSumVoteCount() + ","
                            + getNbUniquePreferences() + "\n");
            for (CompletePreferenceImpl pref : this.getUniquePreferences()) {
                soc.append(getNbVoterForPreference(pref));
                for (Alternative a : CompletePreferenceImpl.toAlternativeSet(
                                pref.getPreferencesNonStrict())) {
                    soc.append("," + a);
                }
                soc.append("\n");
            }
            writer.append(soc);
        }
    }
}
