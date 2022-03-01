package LR1;

import java.io.Serializable;

public class Book implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	private int id;
	
	private String title;
	
	private String isbn;

	private String author;
	
	private int year;
	
	private String publisher;
	
	private BookType type;
	
	public Book() {
	
	}
	public Book(String title, String isbn, String author, int year,String publisher, BookType type) {
		this.setTitle(title);
		this.setIsbn(isbn);
		this.setAuthor(author);
		this.setYear(year);
		this.setPublisher(publisher);
		this.setType(type);
	}
	
	
	public Book clone() {
		Book newBook= new Book(this.getTitle(),this.getIsbn(),this.getAuthor(),this.getYear(),this.getPublisher(),this.getType()); 
		newBook.setId(this.getId());		
		return newBook;
	}
	
	public void copy(Book newBook) {
		newBook.setId(this.getId());		
		newBook.setTitle(this.getTitle());
		newBook.setIsbn(this.getIsbn());
		newBook.setAuthor(this.getAuthor());
		newBook.setYear(this.getYear());
		newBook.setPublisher(this.getPublisher());
		newBook.setType(this.getType());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BookType getType() {
		return type;
	}

	public void setType(BookType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}


enum BookType{printed, ebook}
