import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PatientViewScreen extends VBox {

    private HealthImagingApp app;
    private TextField patientIdField;
    private TextField totalScoreField;
    private TextField lmsField;
    private TextField ladField;
    private TextField lcxField;
    private TextField rcaField;
    private TextField pdaField;
    private Label greeting;

    public PatientViewScreen(HealthImagingApp app) {
        this.app = app;
        initializeUI();		//initialize the UI
    }	

    private void initializeUI() {
        setSpacing(5);
        setPadding(new Insets(20));		//set formatting
        setAlignment(Pos.CENTER);

        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");		//prompt user for patient ID

        greeting = new Label("Please load patient data.");		//use greeting as a placeholder for prompting user to load the patient data

        Button loadButton = new Button("Load Data");
        loadButton.setOnAction(this::loadPatientData);	//when the load button is pressed we will load the patient data by reading the respective files

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> app.showMainScreen());	//back button pressed = go back to main screen

        HBox inputRow = new HBox(10, new Label("Patient ID:"), patientIdField, loadButton, backButton);
        inputRow.setAlignment(Pos.CENTER);		//create a new HBox to prompt the ID input, center it

        totalScoreField = new TextField();
        lmsField = new TextField();
        ladField = new TextField();	//initialize text fields
        lcxField = new TextField();
        rcaField = new TextField();
        pdaField = new TextField();

        makeFieldsNonEditable();		//set everything to noneditable

        this.getChildren().addAll(inputRow, greeting);		//add everything
    }

    private void makeFieldsNonEditable() {
        totalScoreField.setEditable(false);
        lmsField.setEditable(false);
        ladField.setEditable(false);	//function to make everything not editable for simplicity
        lcxField.setEditable(false);
        rcaField.setEditable(false);
        pdaField.setEditable(false);
    }

    private void loadPatientData(ActionEvent event) {
        String patientId = patientIdField.getText().trim();	//set the patient ID to the field text and trim it so theres no white space

        if (patientId.isEmpty()) {
            greeting.setText("Please enter a valid patient ID.");	//check if the field is empty
            return;
        }

        String patientInfoFilename = patientId + "_PatientInfo.txt";	//set the file name to what is desired, then read it
        String patientInfo = readFile(patientInfoFilename);

        if (patientInfo != null) {
            String nameLine = patientInfo.split("\n")[1];
            greeting.setText("Hello " + extractDetail(nameLine));		//check if we can read it, and if we can, greet the user at the top of the screen
        } else {
            greeting.setText("Patient information not available for ID: " + patientId);
        }

        String ctResultsFilename = patientId + "CTResults.txt";
        String ctResultsInfo = readFile(ctResultsFilename);		//set the file name to what is desired, then read the file (this gives us the info we need to display for the patient view screen like the scores)

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> app.showMainScreen());		//back button

        if (ctResultsInfo != null) {
            String[] ctLines = ctResultsInfo.split("\n");	//split everything with a new line
            TextField[] fields = {totalScoreField, lmsField, ladField, lcxField, rcaField, pdaField};		//array of text fields

            for (int i = 0; i < ctLines.length && i < fields.length; i++) {
                fields[i].setText(extractDetail(ctLines[i + 1]));	//iterate thru the length of the lines and the text fields, set the text of each field to what is in each line (the +1 ignores the ID field so we can place everything correctly)
            }	
        }

        this.getChildren().clear();	//clear everything in the screen to set up the actual view
        this.getChildren().addAll(
            greeting,
            createLabelTextFieldPair("Total Score:", totalScoreField),
            new Label("Vessel level Agatston CAC score:"),
            createLabelTextFieldPair("LM:", lmsField),
            createLabelTextFieldPair("LAD:", ladField),
            createLabelTextFieldPair("LCX:", lcxField),		//add every field to look similar to the technician view screen
            createLabelTextFieldPair("RCA:", rcaField),
            createLabelTextFieldPair("PDA:", pdaField),
            backButton
        );
        greeting.setAlignment(Pos.CENTER_LEFT);
    }
    
    private HBox createLabelTextFieldPair(String labelText, TextField textField) {
        Label label = new Label(labelText);
        HBox hbox = new HBox(10, label, textField);		//function to create the labels and the fields in pairs for simplicity
        hbox.setAlignment(Pos.CENTER_LEFT);	//align everything
        return hbox;
    }

    private String readFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {		//read the file using a stringbuilder, try to read in the file using file reader
            String line;
            while ((line = reader.readLine()) != null) {		//while the line isn't null, append the line with the information desired and a new line
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while trying to read the file: " + filename);	//check if we can read the file at all
            return null;
        }
        return content.toString();		//return everything as a string
    }

    private String extractDetail(String line) {
        String[] parts = line.split(":");	//split the line wherever we see : so we can parse the data effectively
        if (parts.length > 1) {		
            return parts[1].trim();	//trim any leading space so we can get the data effectively
        }
        return "";
    }
}
