package LR1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileDAO {
	protected String filepath;
	public FileDAO(String filepath){
		this.setFilepath(filepath);	
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public Object get() {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filepath);
			if (fstream.available()!=0) {
				ObjectInputStream istream= new ObjectInputStream(fstream);
				Object object=istream.readObject();
				istream.close();	
				return object;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return null;

	}
	
	public void save(Object object) {
		FileOutputStream fstream;
		try {
			fstream = new FileOutputStream(filepath);
			ObjectOutputStream ostream= new ObjectOutputStream(fstream);
			ostream.writeObject(object);
			ostream.flush();
			ostream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
	}
}
