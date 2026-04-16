package org.example;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;


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
        Button deleteButton = new Button("Delete Selected");
        Button updateButton = new Button("Update Selected");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(ProductDAO.getProducts());

        addButton.setOnAction(e -> {

            String name = nameField.getText().trim();
            String quantityText = quantityField.getText().trim();


            if (name.isEmpty() || quantityText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill all fields!");
                alert.showAndWait();
                return;
            }

            int quantity;


            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Quantity must be a number!");
                alert.showAndWait();
                return;
            }

            ProductDAO.addProduct(name, quantity);

            listView.getItems().clear();
            listView.getItems().addAll(ProductDAO.getProducts());

            nameField.clear();
            quantityField.clear();
        });

        deleteButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();

            if (selected != null) {
                int id = Integer.parseInt(selected.split(" - ")[0]);

                ProductDAO.deleteProduct(id);

                listView.getItems().remove(selected);
            }
        });

        updateButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();

            if (selected != null) {
                int id = Integer.parseInt(selected.split(" - ")[0]);
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                ProductDAO.updateProduct(id, name, quantity);

                listView.getItems().clear();
                listView.getItems().addAll(ProductDAO.getProducts());

                nameField.clear();
                quantityField.clear();
            }
        });

        listView.setOnMouseClicked(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();

            if (selected != null) {
                String[] parts = selected.split(" - ");
                String idPart = parts[0];

                String[] nameQty = parts[1].split(" \\(");
                String name = nameQty[0];
                String quantity = nameQty[1].replace(")", "");

                nameField.setText(name);
                quantityField.setText(quantity);
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameField, quantityField, addButton, deleteButton, updateButton, listView);

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
