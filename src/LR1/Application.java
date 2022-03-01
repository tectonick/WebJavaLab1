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
			System.out.println("Учет книг в домашней библиотеке\n");
			System.out.println("Главное меню:");
			System.out.println("1:Просмотреть каталог");
			if(umanager.isAdminLogged()) 
				System.out.println("2:Управление пользователями");
			System.out.println("q:Выйти и сохранить");
			
			System.out.println();			
			System.out.println("Введите ваш выбор:");
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
		System.out.println("Учет книг в домашней библиотеке\n");
		System.out.println("Авторизация");
		String login;
		String password;
        do {
			System.out.println("Введите логин:");
			login=reader.readLine();
			System.out.println("Введите пароль: ");
			password=reader.readLine();
		}
		while(!umanager.auth(login,password));
	}
	
	static void userMenu() throws IOException {
		while (true) {
			cleanConsole();
			System.out.println("Учет книг в домашней библиотеке\n");
			System.out.println("Текущие пользователи:");
			System.out.println("Login-Role");
			List<User> users=umanager.getUsers();
			for (User user:users) {
				String role=(user.getRole()==Role.admin)?("Admin"):("User");
				System.out.printf("%s     %s\n", user.getLogin(),role);
			}
			System.out.println();
			
			System.out.println("Управление пользователями:");
			System.out.println("1:Добавить пользователя");
			System.out.println("2:Удалить пользователя");
			System.out.println("b:Назад");
			System.out.println();			

			
			System.out.println();			
			System.out.println("Введите ваш выбор:");
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
		System.out.println("Учет книг в домашней библиотеке\n");			
		System.out.println("Введите логин пользователя для удаления:");
		String choice=reader.readLine();
		try {
			umanager.deleteUser(choice);
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	static void addUserMenu() throws IOException {
		cleanConsole();
		System.out.println("Учет книг в домашней библиотеке\n");			
		System.out.println("Введите логин пользователя для добавления:");		
		String login=reader.readLine();
		System.out.println("Введите пароль пользователя для добавления:");		
		String password=reader.readLine();
		System.out.println("Пользователь с правами администратора (q-да, n-нет):");
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
			System.out.println("Учет книг в домашней библиотеке\n");
			System.out.println("Книги в каталоге:");
			System.out.println("ID-Название-автор-isbn-Год издания-Издатель-Тип книги");
			List<Book> books=catalog.getAllBooks();
			for (Book book:books) {
				String type=(book.getType()==BookType.printed)?("Printed"):("Ebook");
				System.out.printf("%d %s %s %s %d %s %s\n",book.getId(),book.getTitle(),book.getAuthor(),book.getIsbn(), book.getYear(),book.getPublisher(),type );
			}
			System.out.println();
			
			System.out.println("Управление книгами:");
			if(umanager.isAdminLogged()) System.out.println("1:Добавить книгу");
			if(umanager.isAdminLogged()) System.out.println("2:Удалить книгу");
			System.out.println("b:Назад");
			System.out.println();			

			
			System.out.println();			
			System.out.println("Введите ваш выбор:");
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
		System.out.println("Учет книг в домашней библиотеке\n");			
		System.out.println("Введите id книги для удаления:");
		String choice=reader.readLine();
		try {
			catalog.removeBook(catalog.getCopyById(Integer.parseInt(choice)));
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	static void addBookMenu() throws IOException {
		cleanConsole();
		System.out.println("Учет книг в домашней библиотеке\n");			
		System.out.println("Введите название книги для добавления:");		
		String title=reader.readLine();
		System.out.println("Введите isbn книги для добавления:");		
		String isbn=reader.readLine();
		System.out.println("Введите автора книги для добавления:");		
		String author=reader.readLine();
		System.out.println("Введите год издания книги для добавления:");		
		String syear=reader.readLine();
		int year;
		try {
			year=Integer.parseInt(syear);
		} catch (Exception e) {
			System.out.println("Неверный год");
			return;
		}

		
		System.out.println("Введите издателя книги для добавления:");		
		String publisher=reader.readLine();		
		System.out.println("Книга печатная (q-да, n-нет):");
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
		System.out.println("Данные сохранены. Программа успешно завершена.");
	}

}
