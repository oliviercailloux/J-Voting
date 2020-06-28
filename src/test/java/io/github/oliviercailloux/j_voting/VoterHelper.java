package io.github.oliviercailloux.j_voting;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class VoterHelper {
	public static Voter v1 = Voter.createVoter(1);
	public static Voter v2 = Voter.createVoter(2);
	public static Voter v3 = Voter.createVoter(3);
	public static Voter v4 = Voter.createVoter(4);
	public static Set<Voter> v123set = ImmutableSet.of(v1, v2, v3);
}
