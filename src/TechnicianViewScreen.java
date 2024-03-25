import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TechnicianViewScreen extends VBox {

    private HealthImagingApp app;
    private TextField patientIdField;
    private TextField totalScoreField;
    private TextField lmsField;
    private TextField ladField;		//javafx elements
    private TextField lcxField;
    private TextField rcaField;
    private TextField pdaField;
    
    public TechnicianViewScreen(HealthImagingApp app) {
        this.app = app;
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20, 50, 20, 50));
        initializeUI();
    }

    private void initializeUI() {
        patientIdField = new TextField();
        totalScoreField = new TextField();
        lmsField = new TextField();
        ladField = new TextField();		//initialization of javafx elements
        lcxField = new TextField();
        rcaField = new TextField();
        pdaField = new TextField();

        
        HBox idAndScoreBox = new HBox(10, 
            createLabelWithMinWidth("Patient ID:"), patientIdField, 				//create an hbox to store id and score so we can nest fields so it looks like what carter wants
            createLabelWithMinWidth("The total Agatston CAC score:"), totalScoreField
        );
        idAndScoreBox.setAlignment(Pos.CENTER_LEFT);

        
        VBox vesselScoresBox = new VBox(5,
            new Label("Vessel level Agatston CAC score:"),
            createLabelTextFieldPair("LM:", lmsField),
            createLabelTextFieldPair("LAD:", ladField),	//create a vbox for vessel scores to stack them vertically and effectively nest the hbox and vbox
            createLabelTextFieldPair("LCX:", lcxField),	
            createLabelTextFieldPair("RCA:", rcaField),
            createLabelTextFieldPair("PDA:", pdaField)
        );

       
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new TechFormSaveHandler());
        saveButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));	//save button handler, sets the color of the button to blue and specifies that no corner radius or insets are used 
        saveButton.setTextFill(Color.WHITE);
        saveButton.setMaxWidth(Double.MAX_VALUE);

        //add everything to the vbox and set the spacing
        this.getChildren().addAll(idAndScoreBox, vesselScoresBox, saveButton);
        this.setSpacing(20);
    }
    
    private Label createLabelWithMinWidth(String text) {
        Label label = new Label(text);
        label.setMinWidth(Region.USE_PREF_SIZE);
        return label;
    }

    private HBox createLabelTextFieldPair(String labelText, TextField textField) {
        Label label = new Label(labelText);
        HBox hbox = new HBox(10, label, textField);
        hbox.setAlignment(Pos.CENTER_LEFT);
        return hbox;
    }
    
    private class TechFormSaveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            String patientId = patientIdField.getText();
            if (!patientId.isEmpty()) {
                save(patientId + "CTResults.txt",
                    "Patient ID: " + patientId + "\n" +
                    "Total Agatston CAC Score: " + totalScoreField.getText() + "\n" +	//saves all of the data into a file using file writer by calling save
                    "LM: " + lmsField.getText() + "\n" +
                    "LAD: " + ladField.getText() + "\n" +
                    "LCX: " + lcxField.getText() + "\n" +
                    "RCA: " + rcaField.getText() + "\n" +
                    "PDA: " + pdaField.getText() + "\n");
                System.out.println("CT Scan results saved for patient ID: " + patientId);	//notify user everything was saved (you have to go into the properties of the project to see this file (right click the src, go to properties, click the file path)
                app.showMainScreen();
            } else {
                System.out.println("Please enter a Patient ID.");
            }
        }
    }    
    
    private void save(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            writer.write(data);			//saves all the data to file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
