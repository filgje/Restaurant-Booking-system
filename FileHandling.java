
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandling implements IFileHandling{

    @Override
    public void saveRes(String filename, Reservation res) throws IOException{ //Write to file
        FileWriter write = new FileWriter(getFile(filename), true);
        write.append("User: " + res.getBooker().getUsername() + "\n");
        write.append("Pass: " + res.getBooker().getPassword() + "\n");
        write.append(String.format("%s, %s", res.PrinttoString(), res.priceCalc()) + "\n");
        write.flush();
        write.close();
        System.out.println("Saved");
    }

    @Override //Retrieves all the reservations of a specific user
    public String loadRes(String filename, Person current) throws FileNotFoundException {
        Scanner read = new Scanner(getFile(filename));
        String reservation = "";
        while(read.hasNextLine()){
            String x = read.nextLine();
            if (x.startsWith("User: ")){
                String username = x.substring(6);
                String password = read.nextLine().substring(6);
                Person user = new Person(username, password);
                if (current.sameUser(user)){
                    reservation += read.nextLine() + ";";
                    }            
                }
            } 
            read.close();
            return reservation;
    }

    @Override //Retrieves all booked tables and return them in a list
    public List<String> loadTables(String filename) throws FileNotFoundException {
        List<String> MidTableRes = new ArrayList<String>();
        Scanner read = new Scanner(getFile(filename));
        while(read.hasNextLine()){
            String s = read.nextLine();
            if (s.startsWith("Table: ")){
                MidTableRes.add(s);
                }
            }
            read.close();
            return MidTableRes;  
    }

    @Override
    public void deleteRes(String filename, String res) throws FileNotFoundException, IOException {
        Scanner read = new Scanner(getFile(filename));
        List<String> temp = new ArrayList<String>();
        while (read.hasNextLine()){
            String x = read.nextLine();
            temp.add(x);
        }
        read.close();
        
        int deleteIndex = temp.indexOf(res) - 2;
        temp.remove(deleteIndex);
        temp.remove(deleteIndex);
        temp.remove(deleteIndex);

        FileWriter write = new FileWriter(getFile(filename), false);
        for (String string : temp) {
            write.append(string + "\n");
        }
        write.flush();
        write.close();
    }

    public static File getFile(String filename){ //Find the file specified by the program
        return new File(FileHandling.class.getResource("reservations/").getFile() + filename + ".txt");
    }
}
