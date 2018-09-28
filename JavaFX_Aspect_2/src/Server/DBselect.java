package Server;

public class DBselect {
	private String DBtype;
	
	public DBselect(String DB) {
		DBtype = DB;
	}
	
	public String getDBtype() {
		return DBtype;
	}
	
	public String toString() {
		return "Server will be storing data in " + DBtype + " database"; 
				
	}
}
