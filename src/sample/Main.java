package sample;

import java.sql.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // JDBC SQL variables
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

     //Observable list of product objects
    public static ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    // TextFields
    public static TextField productField = new TextField();
    public static TextField quantityField = new TextField();
    public static TextField oldPriceField = new TextField();
    public static TextField newPriceField = new TextField();
    public static TextField teacherClasses = new TextField();

    // Labels
    public static Label productLabel = new Label("Teacher ID");
    public static Label quantityLabel = new Label("First Name");
    public static Label oldPriceLabel = new Label("Last name");
    public static Label newPriceLabel = new Label("Email");



    // Buttons
    public static Button update = new Button("Update");
    public static Button report = new Button("Save");


    @Override
    public void start(Stage primaryStage) throws Exception{

        DatabaseConnectable db = new jdbc();


        rs = db.runQuery("SELECT * FROM teachers");

        // looping trough and creating teachers
        while (rs.next()) {
            int id = rs.getInt(1);
            String first_name = rs.getString(2);
            String last_name = rs.getString(3);
            String email = rs.getString(4);
            teachers.add(new Teacher(id, first_name, last_name, email, db));
        }


        // Creating listView
        ListView<Teacher> list = new ListView<>();
        list.getItems().addAll(teachers);

        // Setting fields uneditable
        productField.setDisable(true);
        teacherClasses.setDisable(true);
        productField.setStyle("-fx-opacity: 1;");
        teacherClasses.setStyle("-fx-opacity: 1;");

        // Horizontal boxes
        HBox oldPriceBox = new HBox(oldPriceField);
        HBox newPriceBox = new HBox(newPriceField);
        HBox classes = new HBox(teacherClasses);

        // Vertical boxes
        VBox labels = new VBox(productLabel, quantityLabel, oldPriceLabel, newPriceLabel);
        VBox desc = new VBox(classes);
        VBox fields = new VBox(productField, quantityField, oldPriceBox, newPriceBox);

        HBox hBox1 = new HBox(labels, fields);
        VBox vBox1 = new VBox(hBox1, desc);

        // Styling
        labels.setPadding(new Insets(15, 12, 15,12));
        labels.setSpacing(10);
        fields.setPadding(new Insets(15,260,10,12));
        desc.setPadding(new Insets(10,0,10,10));


        // Main box
        HBox main = new HBox(vBox1, update, report, list);

        // List item show
        list.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            productField.setText(String.valueOf(newValue.id));
            quantityField.setText(newValue.lastName);
            oldPriceField.setText(newValue.firstName);
            newPriceField.setText(newValue.email);
            ResultSet rs = db.runQuery("SELECT class_name FROM classes JOIN teachers ON id_teacher = idTeachers WHERE id_teacher = " + String.valueOf(newValue.id));
            String classesToShow = "";
            try {
                   while (rs.next()) {
                   classesToShow = classesToShow + rs.getString(1) + ", ";
                }
                teacherClasses.setText(classesToShow);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Update function
        update.setOnAction(actionEvent ->  {
            int selectedIdx = list.getSelectionModel().getSelectedIndex();
            if(selectedIdx != -1) {
                // Get values
                String name = productField.getText();
                String quantity = quantityField.getText();
                String oldPrice = oldPriceField.getText();
                String newPrice = newPriceField.getText();
                // Set values
                list.getItems().get(selectedIdx).setId(Integer.parseInt(name));
                list.getItems().get(selectedIdx).setFirstName(quantity);
                list.getItems().get(selectedIdx).setLastName(oldPrice);
                list.getItems().get(selectedIdx).setEmail(newPrice);
                list.refresh();
            }
        });

        // Save function
        report.setOnAction(actionEvent ->  {
            int selectedIdx = list.getSelectionModel().getSelectedIndex();
            if(selectedIdx != -1) {
                // Get values
                String name = productField.getText();
                String quantity = quantityField.getText();
                String oldPrice = oldPriceField.getText();
                String newPrice = newPriceField.getText();
                // Save values
                db.runQuery("UPDATE teachers SET name = '" + quantity + "', surename = '" + oldPrice + "', email = '" + newPrice + "' WHERE idTeachers= " + name);
            }
        });

        // Creating the scene
        Scene scene = new Scene(main, 890, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
