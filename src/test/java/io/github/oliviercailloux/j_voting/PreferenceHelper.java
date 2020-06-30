package io.github.oliviercailloux.j_voting;

import static io.github.oliviercailloux.j_voting.VoterHelper.v1;
import static io.github.oliviercailloux.j_voting.VoterHelper.v2;
import static io.github.oliviercailloux.j_voting.VoterHelper.v3;
import static io.github.oliviercailloux.j_voting.VoterHelper.v4;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a321list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a231list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1234list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3214list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2314list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a21list;

import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class PreferenceHelper {
	public static MutableLinearPreference mlpv1a123list = MutableLinearPreferenceImpl.given(v1, a123list);
	public static MutableLinearPreference mlpv2a321list = MutableLinearPreferenceImpl.given(v2, a321list);
	public static MutableLinearPreference mlpv3a231list = MutableLinearPreferenceImpl.given(v3, a231list);
	public static MutableLinearPreference mlpv4a321list = MutableLinearPreferenceImpl.given(v4, a321list);

	public static MutableLinearPreference mlpv1a1234list = MutableLinearPreferenceImpl.given(v1, a1234list);
	public static MutableLinearPreference mlpv2a3214list = MutableLinearPreferenceImpl.given(v2, a3214list);
	public static MutableLinearPreference mlpv3a2314list = MutableLinearPreferenceImpl.given(v3, a2314list);

	public static MutableLinearPreference mlpv1a12list = MutableLinearPreferenceImpl.given(v1, a12list);
	public static MutableLinearPreference mlpv2a21list = MutableLinearPreferenceImpl.given(v2, a21list);
	public static MutableLinearPreference mlpv3a21list = MutableLinearPreferenceImpl.given(v3, a21list);
}
