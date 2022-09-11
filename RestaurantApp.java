
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantApp extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("Restaurant Orientato Agli Oggetti");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("RestaurantLogIn.fxml"))));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void ChangeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        if (fxml == "BordReservasjon.fxml"){
            Scene scene = new Scene(pane, 600, 400);
            stage.setScene(scene);
        }
        else if (fxml == "AdminPage.fxml"){
            Scene scene = new Scene(pane, 400, 370);
            stage.setScene(scene);

        }
        else{
            Scene scene = new Scene(pane, 500, 300);
            stage.setScene(scene);
        }
        stage.show();
        
    }
}
