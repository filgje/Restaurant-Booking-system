
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantController {

    //Objects
    public Restaurant restaurant = new Restaurant();
    
    //FXML Objects
    @FXML
    private Button cncl;
    @FXML
    private Button LogIn;
    @FXML
    private TextField UserText;
    @FXML
    private Text errorMessage;
    @FXML
    private PasswordField PassField;
    @FXML
    private Label test;

    @FXML
    public void cancelButtonOnAction(ActionEvent event){ //Connected to Cancel button
        Stage stage = (Stage)cncl.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void LogInCorrect() throws IOException{ //Connected to LogIn button
        RestaurantApp r = new RestaurantApp();
        try {
            Person user = new Person(UserText.getText(), PassField.getText());
            restaurant.addUser(user);
            r.ChangeScene("BordReservasjon.fxml");
        } catch (IllegalArgumentException e) {
            errorMessage.setText("The password must be between 4 and 20 characters long");
        } catch (IllegalStateException e){
            errorMessage.setText("Password or Username is empty");
        }        
    }
}
