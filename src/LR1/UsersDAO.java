package LR1;

import java.util.Vector;

public class UsersDAO extends FileDAO{
	UsersDAO(String filepath){
		super(filepath);
	}
	
	@SuppressWarnings("unchecked")
	public Vector<User> get() {
		return (Vector<User>)super.get();
	}
	public void save(Vector<User> users) {
		super.save(users);		
	}
	
	
}
