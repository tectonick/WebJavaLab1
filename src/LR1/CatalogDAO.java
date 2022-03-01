package LR1;

import java.util.Vector;

public class CatalogDAO extends FileDAO {
	
	CatalogDAO(String filepath){
		super(filepath);	
	}
	
	public Catalog get() {
		return (Catalog)super.get();
	}
	public void save(Vector<User> users) {
		super.save(users);		
	}
}
