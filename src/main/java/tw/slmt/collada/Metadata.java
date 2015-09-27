package tw.slmt.collada;

import java.util.HashSet;
import java.util.Set;

public class Metadata {
	
	private Set<Contributor> contributors = new HashSet<Contributor>();
	
	protected void addContributor(Contributor con) {
		contributors.add(con);
	}
	
	public Set<Contributor> getContributors() {
		return new HashSet<Contributor>(contributors);
	}
}
