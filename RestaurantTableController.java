package exampleproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class RestaurantTableController {
    
    //Objects
    private final List<String> times =  
        Arrays.asList("10:00", "11:00", "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");

    private IFileHandling fileres = new FileHandling();

    private RestaurantApp r = new RestaurantApp();

    private Restaurant restaurant = new Restaurant();

    private Person current = restaurant.getUser();

    //FXML Objects
    @FXML
    private Button cncl;
    @FXML
    private Button reserve;
    @FXML
    private Button openRes;
    @FXML
    private Button watchRes;
    @FXML
    private Button admin;
    @FXML
    private TextArea reservation;
    @FXML
    private TextField NumPers;
    @FXML
    private Text user;
    @FXML
    private Text errorMsg;
    @FXML
    private ChoiceBox<String> quality;
    @FXML
    private ChoiceBox<String> timeStart;
    @FXML
    private ChoiceBox<String> timeStop;
    @FXML
    private DatePicker date;
    @FXML
    private ListView<Table> tableList;

    @FXML
    Alert confirmation = new Alert(AlertType.CONFIRMATION);
    @FXML
    Alert myReservations = new Alert(AlertType.INFORMATION);
    @FXML 
    Alert afterConfirm = new Alert(AlertType.INFORMATION);

    public void initialize(){
        user.setText(current.getUsername());
        quality.setItems(FXCollections.observableArrayList("Poor", "Normal", "Premium"));
        timeStart.setItems(FXCollections.observableArrayList(times));
        timeStop.setItems(FXCollections.observableArrayList(times));

        try {
            current.setPersonRes(fileres.loadRes("file", current));
        } catch (FileNotFoundException e) {
            errorMsg.setText("File not found/Something went wrong");
        }

        if (!current.isAdmin()){
            admin.setDisable(true);;
        }
    }
    
    @FXML
    private void showTables(ActionEvent event) throws IOException{ //Connected to find tables button
        if ((date.getValue() != null && date.getValue().isAfter(LocalDate.now()))){
            if (date.getValue().getDayOfWeek() != DayOfWeek.SUNDAY){
                if (!timeStart.getSelectionModel().isEmpty() && !timeStop.getSelectionModel().isEmpty()){
                    if (!quality.getSelectionModel().isEmpty()){
                        if (!NumPers.getText().isEmpty()){
                            if (timeStart.getSelectionModel().getSelectedIndex() < timeStop.getSelectionModel().getSelectedIndex()){
                                try {
                                    if (Integer.parseInt(NumPers.getText()) > 0 && Integer.parseInt(NumPers.getText()) < 8){
                                        ObservableList<Table> tables = FXCollections.observableArrayList(restaurant.getAvailableTables(Integer.parseInt(NumPers.getText()), quality.getSelectionModel().getSelectedItem()));
                                        tableList.setItems(tables);
                                        reservation.clear();
                                        errorMsg.setText("");
                                    }
                                    else{
                                        reservation.clear();
                                        tableList.setItems(null);
                                        errorMsg.setText("The restaurant only has tables with capacity for up to 7 people");
                                        }
                                } catch (NumberFormatException e) {
                                    reservation.clear();
                                    tableList.setItems(null);
                                    errorMsg.setText("The number of people must be an actual number");
                                    }
                                }
                            else{
                                reservation.clear();
                                tableList.setItems(null);
                                errorMsg.setText("Invalid time");
                                }
                            }
                        else{
                            reservation.clear();
                            tableList.setItems(null);
                            errorMsg.setText("The number of people field is empty");
                        }
                    }
                    else{
                        reservation.clear();
                        tableList.setItems(null);
                        errorMsg.setText("The quality field is empty");
                    }
                }
                else{
                    reservation.clear();
                    tableList.setItems(null);
                    errorMsg.setText("The time fields are empty");
                }   
            }
            else{
                reservation.clear();
                tableList.setItems(null);
                errorMsg.setText("The restaurant is closed on sundays");
            }   
        }
        else{
            reservation.clear();
            tableList.setItems(null);
            errorMsg.setText("Invalid date");
        }               
    }

    @FXML
    private void reservePage(MouseEvent event) throws IOException{ //Connected to the cells in the list
        if (!tableList.getSelectionModel().isEmpty()){
            Table selected = tableList.getSelectionModel().getSelectedItem();
            Reservation res = new Reservation(Integer.parseInt(NumPers.getText()), selected.getSeats(), current, timeStart.getSelectionModel().getSelectedItem(), timeStop.getSelectionModel().getSelectedItem(), date.getValue(), selected.getQuality(), null);
            List<String> tableRaw = fileres.loadTables("file");
            res.setReservations(tableRaw);
            if (res.checkIfTableRes()){
                String content = "This table is reserved";
                reservation.setText(content);
                reserve.setDisable(true);
            }
            else if (current.cantMakeRes()){
                String content = "You have reached the maximum number of reservations (3). Please contact us if you want to delete or alter a reservation.";
                reservation.setText(content);
                reserve.setDisable(true);
            }
            else{
                String content = "Table \nDate: " + res.getDate() + "\nFrom: " + res.getTimeSt() + "\nTo: " + res.getTimeSp() + "\nQuality: " + res.getQual() + "\nNumber of People: " + NumPers.getText() + "/" + selected.getSeats() + "\n\nPrice: " + res.priceCalc();
                reservation.setText(content);
                reserve.setDisable(false);
            }
        }
        else{
            errorMsg.setText("Error");
        }
    }
    
    @FXML
    private void reserve (ActionEvent event) throws IOException{ //Connected to reserve button
        Table selected = tableList.getSelectionModel().getSelectedItem();
        Reservation res = new Reservation(Integer.parseInt(NumPers.getText()),selected.getSeats(), current, timeStart.getSelectionModel().getSelectedItem(), timeStop.getSelectionModel().getSelectedItem(), date.getValue(), quality.getSelectionModel().getSelectedItem(), null);
        confirmation.setContentText("Are you sure you would like to reserve this table?\n" + res.toString());
        Optional<ButtonType> result = confirmation.showAndWait(); //Pop up with reservation confirmation
        if(!result.isPresent()){
            //Nothing happens
        }
        else if(result.get() == ButtonType.OK){
            try {
                fileres.saveRes("file", res);
                afterConfirm.setContentText("Your table is reserved. We look forward to welcoming you!");
                afterConfirm.showAndWait();
                r.ChangeScene("RestaurantLogIn.fxml");
            }
            catch (IOException e){
                System.out.println(e);
            }   
        }
        else if(result.get() == ButtonType.CANCEL){
            confirmation.close();
        }
    }

    @FXML
    private void myReservation(ActionEvent event) throws IOException{ //Connected to my reservations button
        myReservations.setTitle("My reservations");
        try {
            String content = fileres.loadRes("file", current);
            
            if (content != null && content != ""){
                String[] s = content.split(";");
                myReservations.setContentText("Your reservations: \n" + String.join("\n", s));
            }
            else{
                myReservations.setContentText("You do not have any reservations");
            } 
            myReservations.show();
        } catch (IOException e) {
            errorMsg.setText("File not found/Something went wrong");
        }  
    }

    @FXML
    private void AdminMode(ActionEvent event) throws IOException{
        try {
            fileres.loadRes("file", current);
            r.ChangeScene("AdminPage.fxml");
        } catch (FileNotFoundException e) {
            errorMsg.setText("Something went wrong - Reservationfile wasn't found");
        }
    }

    @FXML
    private void GoBack() throws IOException{ //Connected to LogOut button
        r.ChangeScene("RestaurantLogIn.fxml");
    }    

    
}