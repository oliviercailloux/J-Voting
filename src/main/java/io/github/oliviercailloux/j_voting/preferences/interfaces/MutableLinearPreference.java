package io.github.oliviercailloux.j_voting.preferences.interfaces;

import java.util.Set;



import io.github.oliviercailloux.j_voting.Alternative;

public interface MutableLinearPreference extends MutablePreference{
	
	
	//Méthode de changement de position des alternative
	public Set<Alternative> changeOrder(Set<Alternative> alternative);
	
	//Méthode pour supprimer des alternatives
	public void deleteAlternative(Alternative a);
	
}
