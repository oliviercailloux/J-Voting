package io.github.oliviercailloux.j_voting;

import static io.github.oliviercailloux.j_voting.Generator.v1;
import static io.github.oliviercailloux.j_voting.Generator.v2;
import static io.github.oliviercailloux.j_voting.Generator.v3;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class VoterHelper {
	public static Set<Voter> v123set = ImmutableSet.of(v1, v2, v3);
}
