package LR1;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String login;
	private String passwordHash;
	private Role role;
	
	public User(String login, String passwordHash, Role role) {
		this.setLogin(login);
		this.setPasswordHash(passwordHash);
		this.setRole(role);
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}

enum Role{admin, user}


