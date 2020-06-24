package io.github.oliviercailloux.j_voting;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1234list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a21list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2314list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3214list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class AlternativeHelper {

	public static Voter v1 = Voter.createVoter(1);
	public static Voter v2 = Voter.createVoter(2);
	public static Voter v3 = Voter.createVoter(3);
	public static Voter v4 = Voter.createVoter(4);
	public static Set<Voter> v123set = ImmutableSet.of(v1,v2,v3);
    public static Alternative a1 = Alternative.withId(1);
    public static Alternative a2 = Alternative.withId(2);
    public static Alternative a3 = Alternative.withId(3);
    public static Alternative a4 = Alternative.withId(4);
    public static Alternative a5 = Alternative.withId(5);
    public static Alternative a6 = Alternative.withId(6);
    public static ImmutableSet<Alternative> a1singleton = ImmutableSet.of(a1);
    public static ImmutableSet<Alternative> a2singleton = ImmutableSet.of(a2);
    public static ImmutableSet<Alternative> a3singleton = ImmutableSet.of(a3);
    public static ImmutableSet<Alternative> a4singleton = ImmutableSet.of(a4);
    public static ImmutableSet<Alternative> a5singleton = ImmutableSet.of(a5);
    public static ImmutableSet<Alternative> a6singleton = ImmutableSet.of(a6);
    public static ImmutableSet<Alternative> a12 = ImmutableSet.of(a1, a2);
    public static ImmutableSet<Alternative> a13 = ImmutableSet.of(a1, a3);
    public static ImmutableSet<Alternative> a14 = ImmutableSet.of(a1, a4);
    public static ImmutableSet<Alternative> a15 = ImmutableSet.of(a1, a5);
    public static ImmutableSet<Alternative> a21 = ImmutableSet.of(a2, a1);
    public static ImmutableSet<Alternative> a23 = ImmutableSet.of(a2, a3);
    public static ImmutableSet<Alternative> a24 = ImmutableSet.of(a2, a4);
    public static ImmutableSet<Alternative> a25 = ImmutableSet.of(a2, a5);
    public static ImmutableSet<Alternative> a31 = ImmutableSet.of(a3, a1);
    public static ImmutableSet<Alternative> a32 = ImmutableSet.of(a3, a2);
    public static ImmutableSet<Alternative> a34 = ImmutableSet.of(a3, a4);
    public static ImmutableSet<Alternative> a35 = ImmutableSet.of(a3, a5);
    public static ImmutableSet<Alternative> a41 = ImmutableSet.of(a4, a1);
    public static ImmutableSet<Alternative> a42 = ImmutableSet.of(a4, a2);
    public static ImmutableSet<Alternative> a43 = ImmutableSet.of(a4, a3);
    public static ImmutableSet<Alternative> a45 = ImmutableSet.of(a4, a5);
    public static ImmutableSet<Alternative> a46 = ImmutableSet.of(a4, a6);
    public static ImmutableSet<Alternative> a51 = ImmutableSet.of(a5, a1);
    public static ImmutableSet<Alternative> a52 = ImmutableSet.of(a5, a2);
    public static ImmutableSet<Alternative> a53 = ImmutableSet.of(a5, a3);
    public static ImmutableSet<Alternative> a54 = ImmutableSet.of(a5, a4);
    public static ImmutableSet<Alternative> a56 = ImmutableSet.of(a5, a6);
    public static ImmutableSet<Alternative> a123 = ImmutableSet.of(a1, a2, a3);
    public static ImmutableSet<Alternative> a1234 = ImmutableSet.of(a1, a2, a3,
            a4);
    public static ImmutableSet<Alternative> a12345 = ImmutableSet.of(a1, a2, a3,
                    a4, a5);
    public static ImmutableSet<Alternative> a1235 = ImmutableSet.of(a1, a2, a3,
            a5);
    public static ImmutableSet<Alternative> a123546 = ImmutableSet.of(a1, a2, a3,
            a5,a4,a6);
    public static ImmutableSet<Alternative> a1256 = ImmutableSet.of(a1, a2, 
            a5,a6);
    
    public static List<Alternative> a12345list = a12345.asList();
    public static List<Alternative> a123list = a123.asList();
    public static List<Alternative> a1234list = a1234.asList();
    public static List<Alternative> a1235list = a1235.asList();
    public static List<Alternative> a123546list = a123546.asList();
    public static List<Alternative> a46list = a46.asList();
    public static List<Alternative> a1256list = a1256.asList();
    public static List<Alternative> a34list = a34.asList();
    public static List<Alternative> a123456list = ImmutableSet.of(a1, a2, a3, a4, a5,a6).asList();
    public static List<Alternative> a1345list = ImmutableSet.of(a1, a3, a4, a5).asList();
    public static List<Alternative> a345list = ImmutableSet.of(a3, a4, a5).asList();
    public static List<Alternative> a321list = ImmutableSet.of(a3, a2, a1).asList();
    public static List<Alternative> a231list = ImmutableSet.of(a2, a3, a1).asList();
    public static List<Alternative> a41235list = ImmutableSet.of(a4, a1, a2, a3, a5).asList();
    public static List<Alternative> a41325list = ImmutableSet.of(a4, a1, a3, a2, a5).asList();
    public static List<Alternative> a43251list = ImmutableSet.of(a4, a3, a2, a5, a1).asList();
    public static List<Alternative> a3214list = ImmutableSet.of(a3, a2, a1, a4).asList();
    public static List<Alternative> a2314list = ImmutableSet.of(a2, a3, a1, a4).asList();
    
    public static List<Alternative> a52341list = ImmutableSet.of(a5, a2, a3, a4, a1).asList();
    public static List<Alternative> a32541list = ImmutableSet.of(a3, a2, a5, a4, a1).asList();
    public static List<Alternative> a34521list = ImmutableSet.of(a3, a4, a5, a2, a1).asList();
    public static List<Alternative> a54321list = ImmutableSet.of(a5, a4, a3, a2, a1).asList();
    public static List<Alternative> a51324list = ImmutableSet.of(a5, a1, a3, a2, a4).asList();
    
    public static List<Alternative> a41523list = ImmutableSet.of(a4, a1, a5, a2, a3).asList();
    public static List<Alternative> a41253list = ImmutableSet.of(a4, a1, a2, a5, a3).asList();
    public static List<Alternative> a14253list = ImmutableSet.of(a1, a4, a2, a5, a3).asList();
    public static List<Alternative> a14235list = ImmutableSet.of(a1, a4, a2, a3, a5).asList();
    public static List<Alternative> a12list = ImmutableSet.of(a1, a2).asList();
    public static List<Alternative> a21list = ImmutableSet.of(a2,a1).asList();
    public static List<Alternative> a32451list = ImmutableSet.of(a3, a2, a4, a5, a1).asList();
    
    public static MutableLinearPreference v1a123list = MutableLinearPreferenceImpl.given(v1, a123list);
    public static MutableLinearPreference v2a321list = MutableLinearPreferenceImpl.given(v2, a321list);
    public static MutableLinearPreference v3a231list = MutableLinearPreferenceImpl.given(v3, a231list);
    public static MutableLinearPreference v4a321list = MutableLinearPreferenceImpl.given(v4, a321list);
    
    public static MutableLinearPreference v1a1234list = MutableLinearPreferenceImpl.given(v1, a1234list);
    public static MutableLinearPreference v2a3214list = MutableLinearPreferenceImpl.given(v2, a3214list);
    public static MutableLinearPreference v3a2314list = MutableLinearPreferenceImpl.given(v3, a2314list);
    
    public static MutableLinearPreference v1a12list = MutableLinearPreferenceImpl.given(v1, a12list);
    public static MutableLinearPreference v2a21list = MutableLinearPreferenceImpl.given(v2, a21list);
    public static MutableLinearPreference v3a21list = MutableLinearPreferenceImpl.given(v3, a21list);

    

}
