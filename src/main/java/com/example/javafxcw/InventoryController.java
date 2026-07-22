package com.example.javafxcw;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.File;
import java.util.ArrayList;

public class InventoryController {

    @FXML
    private TableView<Part> inventoryTable;

    @FXML
    private ListView<String> lowStockList;

    @FXML
    private Label totalsLabel;

    @FXML
    public void initialize() {
        createTableColumns();
        refreshInventory();
    }

    private void createTableColumns() {
        TableColumn<Part, String> partCodeColumn = new TableColumn<Part, String>("Code");
        partCodeColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("code"));

        TableColumn<Part, String> partNameColumn = new TableColumn<Part, String>("Name");
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));

        TableColumn<Part, String> partBrandColumn = new TableColumn<Part, String>("Brand");
        partBrandColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("brand"));

        TableColumn<Part, Double> partPriceColumn = new TableColumn<Part, Double>("Price");
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        TableColumn<Part, Integer> partQuantityColumn = new TableColumn<Part, Integer>("Quantity");
        partQuantityColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("quantity"));

        TableColumn<Part, String> partCategoryColumn = new TableColumn<Part, String>("Category");
        partCategoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("category"));

        TableColumn<Part, Integer> partThresholdColumn = new TableColumn<Part, Integer>("Threshold");
        partThresholdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("threshold"));

        TableColumn<Part, String> partImageColumn = new TableColumn<Part, String>("Image");
        partImageColumn.setPrefWidth(90);
        partImageColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("image"));
        partImageColumn.setCellFactory(createImageCellFactory());

        inventoryTable.getColumns().clear();
        inventoryTable.getColumns().add(partImageColumn);
        inventoryTable.getColumns().add(partCodeColumn);
        inventoryTable.getColumns().add(partNameColumn);
        inventoryTable.getColumns().add(partBrandColumn);
        inventoryTable.getColumns().add(partPriceColumn);
        inventoryTable.getColumns().add(partQuantityColumn);
        inventoryTable.getColumns().add(partCategoryColumn);
        inventoryTable.getColumns().add(partThresholdColumn);

        inventoryTable.setFixedCellSize(70);
    }


    private Callback<TableColumn<Part, String>, TableCell<Part, String>> createImageCellFactory() {
        return new Callback<TableColumn<Part, String>, TableCell<Part, String>>() {
            @Override
            public TableCell<Part, String> call(TableColumn<Part, String> tableColumn) {
                return new TableCell<Part, String>() {
                    private final ImageView partImageView = new ImageView();

                    {
                        partImageView.setFitWidth(60);
                        partImageView.setFitHeight(60);
                        partImageView.setPreserveRatio(true);
                    }

                    @Override
                    protected void updateItem(String imageFileName, boolean isEmptyCell) {
                        super.updateItem(imageFileName, isEmptyCell);

                        if (isEmptyCell || imageFileName == null || imageFileName.trim().isEmpty()
                                || imageFileName.equalsIgnoreCase("No Image")) {
                            setGraphic(null);
                            setText("");
                            return;
                        }

                        String cleanImageFileName = imageFileName.trim();
                        File imageFile = findImageFile(cleanImageFileName);

                        if (imageFile != null && imageFile.exists()) {
                            try {
                                Image partPicture = new Image(
                                        imageFile.toURI().toString(),
                                        60,
                                        60,
                                        true,
                                        true
                                );
                                partImageView.setImage(partPicture);
                                setGraphic(partImageView);
                                setText(null);
                            } catch (Exception error) {
                                setGraphic(null);
                                setText("Missing");
                            }
                        } else {
                            setGraphic(null);
                            setText("Missing");
                        }
                    }
                };
            }
        };
    }

    @FXML
    void refreshInventory() {
        AppData.inventoryStore.load();
        AppData.inventoryStore.sortByCategoryThenCode();

        ArrayList<Part> allParts = AppData.inventoryStore.getParts();
        inventoryTable.setItems(FXCollections.observableArrayList(allParts));

        int totalPartCount = AppData.inventoryStore.getTotalCount();
        double totalStockValue = AppData.inventoryStore.getTotalValue();
        totalsLabel.setText(
                "Total parts: " + totalPartCount
                        + " | Total value: Rs. " + String.format("%.2f", totalStockValue)
        );

        ArrayList<Part> lowStockParts = AppData.inventoryStore.getLowStockParts();
        ArrayList<String> lowStockLines = new ArrayList<String>();

        for (int index = 0; index < lowStockParts.size(); index++) {
            Part currentPart = lowStockParts.get(index);
            String warningLine = currentPart.getCode()
                    + " - " + currentPart.getName()
                    + " (Quantity " + currentPart.getQuantity()
                    + " / Threshold " + currentPart.getThreshold() + ")";
            lowStockLines.add(warningLine);
        }

        lowStockList.setItems(FXCollections.observableArrayList(lowStockLines));
    }

    @FXML
    void goBackToMenu(ActionEvent actionEvent) {
        SceneNavigator.goTo(actionEvent, "MenuView.fxml");
    }

    private File findImageFile(String imageFileName) {
        File imageInProjectFolder = new File("images/" + imageFileName);
        if (imageInProjectFolder.exists()) {
            return imageInProjectFolder;
        }

        String currentWorkingFolder = System.getProperty("user.dir");
        File imageFromWorkingFolder = new File(currentWorkingFolder, "images/" + imageFileName);
        if (imageFromWorkingFolder.exists()) {
            return imageFromWorkingFolder;
        }

        return imageInProjectFolder;
    }
}