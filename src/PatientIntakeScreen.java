import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class PatientIntakeScreen extends VBox{
	private HealthImagingApp app;
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField emailField;	//javafx elements
	private TextField insuranceIdField;
	private TextField phoneField;
	private TextField healthHistoryField;
	private String patientId;
	
	public PatientIntakeScreen(HealthImagingApp app) {
        this.app = app;
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);			//format everything
        setPadding(new Insets(20));
        initializeUI();
    }

	
	private void initializeUI() {
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();	//initialization of javafx elems
        phoneField = new TextField();
        healthHistoryField = new TextField();
        insuranceIdField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new PatientFormSaveHandler());	//make the save button actually do something
        // Styling the button
        saveButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setMaxWidth(Double.MAX_VALUE);


        
        getChildren().addAll(
            createLabelTextFieldPair("First Name:", firstNameField),
            createLabelTextFieldPair("Last Name:", lastNameField),
            createLabelTextFieldPair("Email:", emailField),				//add all form elements
            createLabelTextFieldPair("Phone Number:", phoneField),
            createLabelTextFieldPair("Health History:", healthHistoryField),
            createLabelTextFieldPair("Insurance ID:", insuranceIdField),
            saveButton
        );
    }

    private HBox createLabelTextFieldPair(String labelText, TextField textField) {
        Label label = new Label(labelText);
        HBox hbox = new HBox(10, label, textField);		//need to stack elements so we'll make a new hbox that creates pairs of labels and textfields so everything looks nice like carter wants
        hbox.setAlignment(Pos.CENTER_LEFT);
        return hbox;
    }
	
    private class PatientFormSaveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            patientId = generatePatientId();		//upon pressing the button a new unique ID is created for the patient and creates the files
            
            String data = String.format(
                "Patient ID: %s%nFirst Name: %s%nLast Name: %s%nEmail: %s%nPhone: %s%nHealth History: %s%nInsurance ID: %s%n",
                patientId,
                firstNameField.getText(),
                lastNameField.getText(),		//format the data as follows
                emailField.getText(),
                phoneField.getText(),
                healthHistoryField.getText(),
                insuranceIdField.getText()
            );

           
            if (save(patientId + "_PatientInfo.txt", data)) {
                System.out.println("Patient Data saved successfully for ID: " + patientId);
                app.showMainScreen(); //if we were able to save, go back to the main screen, if not, show an error message
            } else {
                System.out.println("Failed to save Patient Data.");
            }
        }
    }
    
    private String generatePatientId() {
        Random random = new Random();
        int number = random.nextInt(90000) + 10000; //generates random 5 digit number for the ID and returns it in a string
        return String.valueOf(number);
    }

    private boolean save(String filename, String data) {			//method to save the data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            writer.write(data);
            return true; 		//if we can write the data, return true, else we will catch an IOexception and return false
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
    }
	

}