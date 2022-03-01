package LR1;

import java.io.*;
import java.util.List;


public class Application {
	
	static final String USERSPATH="users.txt";
	static final String CATALOGPATH="catalog.txt";
	
	static UsersDAO usersDAO=new UsersDAO(USERSPATH);
	static CatalogDAO catalogDAO=new CatalogDAO(CATALOGPATH);
	static UserManager umanager=new UserManager(usersDAO);
	static Catalog catalog;
	
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	static void cleanConsole() {
		//System.out.print("\033[H\033[2J");
		//System.out.flush();
	    try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException ex) {}
	}
	static void mainMenu() throws IOException {
		while (true) {
			cleanConsole();
			System.out.println("���� ���� � �������� ����������\n");
			System.out.println("������� ����:");
			System.out.println("1:����������� �������");
			if(umanager.isAdminLogged()) 
				System.out.println("2:���������� ��������������");
			System.out.println("q:����� � ���������");
			
			System.out.println();			
			System.out.println("������� ��� �����:");
			String choice=reader.readLine();
			switch(choice) {
				case "1": 
					catalogMenu();
					break;
				case "2":
					if(umanager.isAdminLogged()) userMenu();
					break;
				case "q": return;
			}
		}
	}
	
	static void authorizationMenu() throws IOException {
		System.out.println("���� ���� � �������� ����������\n");
		System.out.println("�����������");
		String login;
		String password;
        do {
			System.out.println("������� �����:");
			login=reader.readLine();
			System.out.println("������� ������: ");
			password=reader.readLine();
		}
		while(!umanager.auth(login,password));
	}
	
	static void userMenu() throws IOException {
		while (true) {
			cleanConsole();
			System.out.println("���� ���� � �������� ����������\n");
			System.out.println("������� ������������:");
			System.out.println("Login-Role");
			List<User> users=umanager.getUsers();
			for (User user:users) {
				String role=(user.getRole()==Role.admin)?("Admin"):("User");
				System.out.printf("%s     %s\n", user.getLogin(),role);
			}
			System.out.println();
			
			System.out.println("���������� ��������������:");
			System.out.println("1:�������� ������������");
			System.out.println("2:������� ������������");
			System.out.println("b:�����");
			System.out.println();			

			
			System.out.println();			
			System.out.println("������� ��� �����:");
			String choice=reader.readLine();
			switch(choice) {
				case "1": 
					addUserMenu();
					break;
				case "2":
					deleteUserMenu();
					break;
				case "b": return;
			}
		}
	}
	
	static void deleteUserMenu() throws IOException {
		cleanConsole();
		System.out.println("���� ���� � �������� ����������\n");			
		System.out.println("������� ����� ������������ ��� ��������:");
		String choice=reader.readLine();
		try {
			umanager.deleteUser(choice);
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	static void addUserMenu() throws IOException {
		cleanConsole();
		System.out.println("���� ���� � �������� ����������\n");			
		System.out.println("������� ����� ������������ ��� ����������:");		
		String login=reader.readLine();
		System.out.println("������� ������ ������������ ��� ����������:");		
		String password=reader.readLine();
		System.out.println("������������ � ������� �������������� (q-��, n-���):");
		String choice=reader.readLine();
		Role role;
		switch(choice) {
			case "q": 
				role=Role.admin;
				break;
			default:
				role=Role.user;
				break;
		}
		
		try {
			umanager.registerUser(login, password, role);
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	static void catalogMenu() throws IOException {
		while (true) {
			cleanConsole();
			System.out.println("���� ���� � �������� ����������\n");
			System.out.println("����� � ��������:");
			System.out.println("ID-��������-�����-isbn-��� �������-��������-��� �����");
			List<Book> books=catalog.getAllBooks();
			for (Book book:books) {
				String type=(book.getType()==BookType.printed)?("Printed"):("Ebook");
				System.out.printf("%d %s %s %s %d %s %s\n",book.getId(),book.getTitle(),book.getAuthor(),book.getIsbn(), book.getYear(),book.getPublisher(),type );
			}
			System.out.println();
			
			System.out.println("���������� �������:");
			if(umanager.isAdminLogged()) System.out.println("1:�������� �����");
			if(umanager.isAdminLogged()) System.out.println("2:������� �����");
			System.out.println("b:�����");
			System.out.println();			

			
			System.out.println();			
			System.out.println("������� ��� �����:");
			String choice=reader.readLine();
			switch(choice) {
				case "1": 
					if(umanager.isAdminLogged()) addBookMenu();
					
					break;
				case "2":
					if(umanager.isAdminLogged()) deleteBookMenu();
					
					break;
				case "b": return;
			}
		}
	}
	
	static void deleteBookMenu() throws IOException {
		cleanConsole();
		System.out.println("���� ���� � �������� ����������\n");			
		System.out.println("������� id ����� ��� ��������:");
		String choice=reader.readLine();
		try {
			catalog.removeBook(catalog.getCopyById(Integer.parseInt(choice)));
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	static void addBookMenu() throws IOException {
		cleanConsole();
		System.out.println("���� ���� � �������� ����������\n");			
		System.out.println("������� �������� ����� ��� ����������:");		
		String title=reader.readLine();
		System.out.println("������� isbn ����� ��� ����������:");		
		String isbn=reader.readLine();
		System.out.println("������� ������ ����� ��� ����������:");		
		String author=reader.readLine();
		System.out.println("������� ��� ������� ����� ��� ����������:");		
		String syear=reader.readLine();
		int year;
		try {
			year=Integer.parseInt(syear);
		} catch (Exception e) {
			System.out.println("�������� ���");
			return;
		}

		
		System.out.println("������� �������� ����� ��� ����������:");		
		String publisher=reader.readLine();		
		System.out.println("����� �������� (q-��, n-���):");
		String choice=reader.readLine();
		BookType type;
		switch(choice) {
			case "q": 
				type=BookType.printed;
				break;
			default:
				type=BookType.ebook;
				break;
		}
		
		try {
			Book newBook=new Book(title,isbn,author,year,publisher,type);
			catalog.addBook(newBook);
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	

	
	public static void main(String[] args) throws IOException {
		
		//catalog.addBook(new Book("Harry Potter", "978-0439708180","J.K. Rowling",1998, "Scholastic", BookType.printed), umanager.getCurrentUser());
		//umanager.initAdmin();
		authorizationMenu();
		
		catalog=catalogDAO.get();
		if (catalog==null) catalog=new Catalog();

        mainMenu();  
		
		catalogDAO.save(catalog);
		System.out.println("������ ���������. ��������� ������� ���������.");
	}

}
