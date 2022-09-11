import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class AdminPageController {
    
    @FXML
    private Button cancel;
    @FXML
    private Button back;
    @FXML
    private ListView<String> ListAllRes;
    @FXML
    private Text error;

    RestaurantApp r = new RestaurantApp();

    FileHandling fh = new FileHandling();

    public void initialize() throws FileNotFoundException{
        try {
            ObservableList<String> st = FXCollections.observableArrayList(fh.loadTables("file"));
            ListAllRes.setItems(st); 
        } catch (FileNotFoundException e) {
            error.setText("File was not found");
        }
        
    }

    @FXML
    private void cancelBooking(ActionEvent event) throws IOException{
        String select = ListAllRes.getSelectionModel().getSelectedItem();
        try {
            fh.deleteRes("file", select);
            ObservableList<String> st = FXCollections.observableArrayList(fh.loadTables("file"));
            ListAllRes.setItems(st);
        } catch (IOException e) {
            error.setText("Something went wong - couldn't find file");
        }        
    }

    @FXML
    private void GoBack(ActionEvent event) throws IOException{ //Connected to Log Out button
        r.ChangeScene("BordReservasjon.fxml");
    }    


}
