import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class HealthImagingApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;	//create root pane, which will be a borderpane for the main stage we will be working on

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;	//upon staring, set the primary stage to the primary stage defined, and create a borderpane for the root layout
        this.rootLayout = new BorderPane();

        primaryStage.setTitle("Heart Health Imaging and Recording System");
        showMainScreen();	//set the title and show the main stage
        primaryStage.show();
    }

    public void showMainScreen() {
        VBox menu = new VBox(10);	//make new vbox
        menu.setAlignment(Pos.CENTER); //center every elem
        
        Label greeting = new Label("Welcome to Heart Health Imaging and Recording System");
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 20));			//set the font of the label to arial and then make it blue
        greeting.setTextFill(Color.BLUE);

      
        Button patientButton = createButton("Patient Intake");
        Button technicianButton = createButton("CT Scan Tech View");	//create buttons
        Button patientViewButton = createButton("Patient View");

        
        patientButton.setOnAction(new PatientIntakeScreenHandler());
        technicianButton.setOnAction(new TechViewScreenHandler());		//set handlers so the buttons actually do things
        patientViewButton.setOnAction(new PatientViewScreenHandler());

        
        menu.getChildren().addAll(greeting, patientButton, technicianButton, patientViewButton);	//add everything

        rootLayout.setCenter(menu); //center the menu

        
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(rootLayout, 600, 400));			//only create a new scene and set it on the primary stage if it has not been set before (handle overriding stages)
        } else {
            primaryStage.getScene().setRoot(rootLayout);
        }
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));	//this method just makes my life easier, essentially it just sets the background of the buttons, the color, font, etc.  I use this and call it on each button that is created for the main screen
        button.setTextFill(Color.WHITE);					
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setMaxWidth(Double.MAX_VALUE); 
        return button;	//return the button on a successful call
    }


    public void showPatientIntakeScreen() {
        PatientIntakeScreen patientIntakeScreen = new PatientIntakeScreen(this);	//method to show patient intake
        rootLayout.setCenter(patientIntakeScreen);
    }

    public void showTechnicianViewScreen() {
        TechnicianViewScreen techViewScreen = new TechnicianViewScreen(this);		//method to show technician view screen
        rootLayout.setCenter(techViewScreen);
    }

    public void showPatientViewScreen() {
        PatientViewScreen patientViewScreen = new PatientViewScreen(this);			//method to show patient view screen
        rootLayout.setCenter(patientViewScreen);
    }
    

    private class PatientIntakeScreenHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            showPatientIntakeScreen();		//button pressed = show patient intake screen
        }
    }

    private class TechViewScreenHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            showTechnicianViewScreen();		//button pressed = show tech view screen
        }
    }

    private class PatientViewScreenHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            showPatientViewScreen();		//button pressed = show patient view screen
        }
    }

    public static void main(String[] args) {
        launch(args);		//launch the screen (main method)
    }
}
