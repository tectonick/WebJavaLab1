package LR1;
import java.io.Serializable;
import java.util.*;


public class Catalog implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Book> books;
	
	 private Book getBookById(int id) {
		 for (Book book : books) {
			 if (book.getId()==id) {
				 return book;
			 }
		 }
		 return null;
	 }
	 
	 public Book getCopyById(int id) {
		 for (Book book : books) {
			 if (book.getId()==id) {
				 return book.clone();
			 }
		 }
		 return null;
	 }
	 
	 
	 public Book getCopyByIsbn(String isbn) {
		 for (Book book : books) {
			 if (book.getIsbn().equals(isbn)) {
				 return book.clone();
			 }
		 }
		 return null;
	 }
	 
	 public Catalog() {
		 books=new Vector<Book>();
	 }

	 
	 public List<Book> getAllBooks(){
		 return books;
	 }
	 
	 public void addBook(Book book) {
			 int newId=books.size();
			 book.setId(newId);
			 books.add(book);
	 }
	 public void removeBook(Book book) throws Exception {
		 	if (book==null) throw new Exception("Не найдена книга");
			 Book realBookToDelete=getBookById(book.getId());
			 books.remove(realBookToDelete);
	 }
	 public void updateBook(Book book) {
			 Book realBookToUpdate=getBookById(book.getId());
			 book.copy(realBookToUpdate);
	 }
}
