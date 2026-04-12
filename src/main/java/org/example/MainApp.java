package org.example;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


import javax.swing.text.BoxView;
import java.awt.*;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        TextField nameField = new TextField();
        nameField.setPromptText("Product name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        Button addButton = new Button("Add product");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(ProductDAO.getProducts());

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            ProductDAO.addProduct(name, quantity);

            listView.getItems().add(name + " (" + quantity + ")");

            nameField.clear();
            quantityField.clear();

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameField, quantityField, addButton, listView);

        Scene scene = new Scene(layout, 300, 400);

        stage.setTitle("Inventory App");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        ProductDAO.createTable();
        launch();
    }

}
