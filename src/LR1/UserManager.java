package LR1;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class UserManager {
	private User currentUser;
	private Vector<User> users;
	UsersDAO usersDAO;
	
	public boolean isAdminLogged() {
		return currentUser.getRole()==Role.admin;
	}
	
	public UserManager(UsersDAO usersDAO){
		this.usersDAO=usersDAO;
		users=this.usersDAO.get();
		if (users==null) {
			users=new Vector<User>();
		}
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public boolean auth(String login, String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String passHash = new String(messageDigest.digest());
			for(User user:users) {
				 if (user.getLogin().equals(login)&&user.getPasswordHash().equals(passHash)) {
					 currentUser=user;
					 return true;
				 }
			}		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	@SuppressWarnings("unused")
	private void initAdmin() {
		String login="admin";
		String password="12345";
		Role role=Role.admin;

			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(password.getBytes());
				String passHash = new String(messageDigest.digest());
				User newUser=new User(login, passHash, role);
				users.add(newUser);
				usersDAO.save(users);	
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void registerUser(String login, String password, Role role) throws Exception {
		if(currentUser.getRole()==Role.admin) {
			User existingUser=null;
			for (User user:users) {
				 if (user.getLogin().equals(login)) {
					 existingUser=user;
					 break;
				 }	
			}
			if(existingUser!=null) throw new Exception("ѕользователь с таким именем уже существует");
			
			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(password.getBytes());
				String passHash = new String(messageDigest.digest());
				User newUser=new User(login, passHash, role);
				users.add(newUser);
				usersDAO.save(users);	
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	public void deleteUser(String login) throws Exception {
		if(currentUser.getRole()==Role.admin) {
			if (currentUser.getLogin().equals(login)) throw new Exception("Ќельз€ удалить текущего пользовател€");
			User userToDelete=null;
			for (User user:users) {
				 if (user.getLogin().equals(login)) {
					 userToDelete=user;
					 break;
				 }	
			}
			if (userToDelete!=null) {
				users.remove(userToDelete);
				usersDAO.save(users);	
			}
			else {
				throw new Exception("Ќе найден такой пользователь");
			}
					
		}
	}
}
