package io.github.oliviercailloux.j_voting;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv1a1234list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv1a123list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv1a12list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv2a21list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv2a3214list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv2a321list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv3a21list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv3a2314list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv3a231list;
import static io.github.oliviercailloux.j_voting.PreferenceHelper.mlpv4a321list;
import static io.github.oliviercailloux.j_voting.Voter.v1;
import static io.github.oliviercailloux.j_voting.Voter.v2;
import static io.github.oliviercailloux.j_voting.Voter.v3;
import static io.github.oliviercailloux.j_voting.Voter.v4;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import io.github.oliviercailloux.j_voting.preferences.MutableLinearPreference;
import io.github.oliviercailloux.j_voting.profiles.MutableStrictProfile;

public class ProfileHelper {
	public static BiMap<Alternative, String> alternativeNamesTPF = ImmutableBiMap.of(a1, "Tartiflette", a2,
			"Pâte au saumon", a3, "Fondue Savoyarde");
	public static BiMap<Alternative, String> alternativeNamesTPFA = ImmutableBiMap.of(a1, "Tartiflette", a2,
			"Pâte au saumon", a3, "Fondue Savoyarde", a4, "Alternative 4");
	public static BiMap<Alternative, String> alternativeNamesTCF = ImmutableBiMap.of(a1, "Tartiflette", a2, "Couscous",
			a3, "Fondue Savoyarde");
	public static BiMap<Alternative, String> alternativeNamesTP = ImmutableBiMap.of(a1, "Tartiflette", a2,
			"Pâte au saumon");

	public static BiMap<Voter, String> voterNamesLTJ4 = ImmutableBiMap.of(v1, "Pierre", v2, "Thomas", v3, "Jade", v4,
			"Voter 4");
	public static BiMap<Voter, String> voterNamesPT = ImmutableBiMap.of(v1, "Pierre", v2, "Thomas");
	public static BiMap<Voter, String> voterNamesLTJ = ImmutableBiMap.of(v1, "Léo", v2, "Thomas", v3, "Jade");
	public static BiMap<Voter, String> voterNamesPTJ = ImmutableBiMap.of(v1, "Pierre", v2, "Thomas", v3, "Jade");

	public static Map<Voter, MutableLinearPreference> profilea123v1234 = ImmutableMap.of(v1, mlpv1a123list, v2,
			mlpv2a321list, v3, mlpv3a231list, v4, mlpv4a321list);
	public static Map<Voter, MutableLinearPreference> profilea12v12 = ImmutableMap.of(v1, mlpv1a123list, v2,
			mlpv2a321list);
	public static Map<Voter, MutableLinearPreference> profilea123v123 = ImmutableMap.of(v1, mlpv1a123list, v2,
			mlpv2a321list, v3, mlpv3a231list);
	public static Map<Voter, MutableLinearPreference> profilea1234v123 = ImmutableMap.of(v1, mlpv1a1234list, v2,
			mlpv2a3214list, v3, mlpv3a2314list);
	public static Map<Voter, MutableLinearPreference> profilea123v12 = ImmutableMap.of(v1, mlpv1a12list, v2,
			mlpv2a21list, v3, mlpv3a21list);

	public static MutableStrictProfile mspa123v1234 = MutableStrictProfile.given(profilea123v1234, alternativeNamesTPF,
			voterNamesLTJ4);
	public static MutableStrictProfile mspa12v12 = MutableStrictProfile.given(profilea12v12, alternativeNamesTPF,
			voterNamesPT);
	public static MutableStrictProfile mspa123v123 = MutableStrictProfile.given(profilea123v123, alternativeNamesTPF,
			voterNamesLTJ);
	public static MutableStrictProfile mspa1234v123 = MutableStrictProfile.given(profilea1234v123, alternativeNamesTPFA,
			voterNamesPTJ);
	public static MutableStrictProfile mspa123v12 = MutableStrictProfile.given(profilea123v12, alternativeNamesTP,
			voterNamesPTJ);
	public static MutableStrictProfile mspa123v123renameA = MutableStrictProfile.given(profilea123v123,
			alternativeNamesTCF, voterNamesPTJ);
}
