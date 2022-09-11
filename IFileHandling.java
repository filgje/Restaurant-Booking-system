package exampleproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFileHandling {
    
    void saveRes(String filename, Reservation res) throws IOException;

    String loadRes(String filename, Person current) throws FileNotFoundException;

    List<String> loadTables(String filename) throws FileNotFoundException;

    void deleteRes(String filename, String res) throws FileNotFoundException, IOException;
}
