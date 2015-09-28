package tw.slmt.collada.parse;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Metadata {
	
	public enum UpAxis { X_UP, Y_UP, Z_UP }
	
	private Set<Contributor> contributors = new HashSet<Contributor>();
	
	protected Calendar createdTime;
	protected Calendar modifiedTime;
	
	protected Set<String> keywords;
	protected String revision;
	protected String subject;
	protected String title;
	
	// Measurements
	protected double metersPerUnit = 1.0;
	protected String unitName = "meter";
	protected UpAxis upAxis = UpAxis.Y_UP;
	
	protected void addContributor(Contributor con) {
		contributors.add(con);
	}
	
	// ===========
	//   Getters
	// ===========
	
	public Set<Contributor> getContributors() {
		return new HashSet<Contributor>(contributors);
	}
	
	public Calendar getCreatedTime() {
		return (Calendar) createdTime.clone();
	}
	
	public Calendar getModifiedTime() {
		return (Calendar) modifiedTime.clone();
	}
	
	public Set<String> getKeywords() {
		return new HashSet<String>(keywords);
	}
	
	public String getRevision() {
		return revision;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getTitle() {
		return title;
	}
	
	public double getMetersPerUnit() {
		return metersPerUnit;
	}
	
	public String getUnitName() {
		return unitName;
	}
	
	public UpAxis getUpAxis() {
		return upAxis;
	}
}
