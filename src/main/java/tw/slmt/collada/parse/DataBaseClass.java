package tw.slmt.collada.parse;

public class DataBaseClass {
	protected String id;
	protected String name;
	
	public DataBaseClass() {
		id = "";
		name = "";
	}
	
	public DataBaseClass(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
