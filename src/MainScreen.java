import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainScreen extends VBox {
    
    private HealthImagingApp app;
    
    public MainScreen(HealthImagingApp app) {
        this.app = app;
        initializeUI();		//initialize the UI
    }
    
    private void initializeUI() {
        Label greeting = new Label("Welcome to Heart Health Imaging and Recording System");
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 20));		//set greeting

        Button intakeButton = new Button("Patient Intake");
        Button technicianButton = new Button("CT Scan Tech View");	//instantiate buttons
        Button patientViewButton = new Button("Patient View");

        intakeButton.setOnAction(new ShowPatientIntakeHandler(app));
        technicianButton.setOnAction(new ShowTechnicianViewHandler(app));	//make buttons actually do stuff
        patientViewButton.setOnAction(new ShowPatientViewHandler(app));

        this.getChildren().addAll(greeting, intakeButton, technicianButton, patientViewButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);		//center and format everything
        this.setPadding(new Insets(10, 10, 10, 10));
    }
        
    private static class ShowPatientIntakeHandler implements EventHandler<ActionEvent> {
        private HealthImagingApp app;
        
        public ShowPatientIntakeHandler(HealthImagingApp app) {		//handles showing the patient intake screen
            this.app = app;
        }
        
        public void handle(ActionEvent event) {
            app.showPatientIntakeScreen();
        }
    }
    
    private static class ShowTechnicianViewHandler implements EventHandler<ActionEvent> {
        private HealthImagingApp app;
        
        public ShowTechnicianViewHandler(HealthImagingApp app) {		//handles showing the tech view screen
            this.app = app;
        }
        
        public void handle(ActionEvent event) {
            app.showTechnicianViewScreen();
        }
    }
    
    private static class ShowPatientViewHandler implements EventHandler<ActionEvent> {
        private HealthImagingApp app;
        
        public ShowPatientViewHandler(HealthImagingApp app) {		//handles showing the patient view
            this.app = app;		
        }
       
        public void handle(ActionEvent event) {
            app.showPatientViewScreen();
        }
    }
}
