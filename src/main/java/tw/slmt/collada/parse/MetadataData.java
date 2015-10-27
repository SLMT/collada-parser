package tw.slmt.collada.parse;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MetadataData {
	
	public enum UpAxis { X_UP, Y_UP, Z_UP }
	
	private Set<ContributorData> contributors = new HashSet<ContributorData>();
	
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
	
	protected void addContributor(ContributorData con) {
		contributors.add(con);
	}
	
	// ===========
	//   Getters
	// ===========
	
	public Set<ContributorData> getContributors() {
		return new HashSet<ContributorData>(contributors);
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
