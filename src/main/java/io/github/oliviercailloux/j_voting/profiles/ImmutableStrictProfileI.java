package io.github.oliviercailloux.j_voting.profiles;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;

/**
 * This class is immutable. Represents a Strict Incomplete Profile.
 */
public class ImmutableStrictProfileI extends ImmutableProfileI
                implements StrictProfileI {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ImmutableStrictProfileI.class.getName());

    /**
     * Factory method for ImmutableStrictProfileI
     * 
     * @param map <code>not null</code> and complete
     * @return new ImmutableStrictProfileI
     */
    public static ImmutableStrictProfileI createImmutableStrictProfileI(
                    Map<Voter, ? extends OldCompletePreferenceImpl> map) {
        LOGGER.debug("Factory ImmutableStrictProfileI");
        checkStrictMap(map);
        return new ImmutableStrictProfileI(map);
    }

    protected ImmutableStrictProfileI(
                    Map<Voter, ? extends OldCompletePreferenceImpl> map) {
        super(map);
        LOGGER.debug("ImmutableStrictProfileI  constructor");
    }

    @Override
    public OldLinearPreferenceImpl getPreference(Voter v) {
        LOGGER.debug("getPreference:");
        Preconditions.checkNotNull(v);
        if (!votes.containsKey(v)) {
            throw new NoSuchElementException(
                            "Voter " + v + "is not in the map !");
        }
        return votes.get(v).toStrictPreference();
    }

    @Override
    public List<String> getIthAlternativesAsStrings(int i) {
        LOGGER.debug("getIthAlternativesStrings");
        if (i > getMaxSizeOfPreference()) {
            throw new IndexOutOfBoundsException(
                            "The given index is out of bound.");
        }
        List<String> list = new ArrayList<>();
        for (Voter v : getAllVoters()) {
            OldLinearPreferenceImpl p = getPreference(v);
            LOGGER.debug("the voter {} votes for the preference {}", v, p);
            if (i >= p.size()) {
                list.add("");
                LOGGER.debug("the preference is smaller than the given index");
            } else {
                list.add(p.getAlternative(i).toString());
                LOGGER.debug("the ith alternative is {}", p.getAlternative(i));
            }
        }
        return list;
    }

    @Override
    public List<String> getIthAlternativesOfUniquePrefAsString(int i) {
        LOGGER.debug("getIthAlternativesOfUniquePrefAsString");
        Preconditions.checkNotNull(i);
        List<String> list = new ArrayList<>();
        for (OldCompletePreferenceImpl p : getUniquePreferences()) {
            String alter = "";
            if (i < p.size()) {
                alter = p.getAlternative(i).toString();
                LOGGER.debug("the ith alternative is {}", p.getAlternative(i));
            }
            list.add(alter);
        }
        return list;
    }

    @Override
    public void writeToSOI(OutputStream output) throws IOException {
        LOGGER.debug("writeToSOI :");
        Preconditions.checkNotNull(output);
        try (Writer writer = new BufferedWriter(
                        new OutputStreamWriter(output))) {
            StringBuilder soi = new StringBuilder();
            soi.append(getNbAlternatives() + "\n");
            for (Alternative alter : getAlternatives()) {
                soi.append(alter.getId() + "\n");
            }
            soi.append(getNbVoters() + "," + getSumVoteCount() + ","
                            + getNbUniquePreferences() + "\n");
            for (OldCompletePreferenceImpl pref : this.getUniquePreferences()) {
                soi.append(getNbVoterForPreference(pref));
                for (Alternative a : OldCompletePreferenceImpl.toAlternativeSet(
                                pref.getPreferencesNonStrict())) {
                    soi.append("," + a);
                }
                soi.append("\n");
            }
            writer.append(soi);
        }
    }
}
