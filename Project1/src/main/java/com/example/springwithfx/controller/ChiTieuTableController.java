package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.service.ChiTieuService;
import com.example.springwithfx.views.WarningDialog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class ChiTieuTableController extends AbstractController implements Initializable  {
    private Stage stage;
    private Scene scene;

    private final StageManager stageManager;
    private final ChiTieuService chiTieuService;

    @FXML
    public TableView<ChiTieu> chiTieuTable;
    @FXML
    private Label totalLabel;
    public TableColumn actionColumn;
    public TableColumn valueColumn;
    public TableColumn ngayChiTieuColumn;
    public TextField searchField;

    public ChiTieuTableController(StageManager stageManager, ChiTieuService chiTieuService) {
        this.stageManager = stageManager;
        this.chiTieuService = chiTieuService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChiTieuTableController controller = this;
        actionColumn.setCellFactory(param -> new TableCell<ChiTieu, Void>() {
            private final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            private final FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
            private final HBox pane = new HBox(deleteIcon, editIcon);

            {
                deleteIcon.setStyle(" -fx-cursor: hand ;"
                        + "-glyph-size:28px;"
                        + "-fx-fill:#ff1744;"
                );
                editIcon.setStyle(
                        " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill: #00E676;"
                );
                pane.setSpacing(30);
                pane.setPadding(new Insets(0, 0, 0, 45));
                deleteIcon.setOnMouseClicked(event -> {
                            WarningDialog warningDialog = new WarningDialog();
                            Optional<ButtonType> result = warningDialog.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.YES) {
                                ChiTieu chiTieu = getTableView().getItems().get(getIndex());
                                chiTieuService.deleteChiTieu(chiTieu.getId());
                                chiTieuTable.getItems().remove(getIndex());
                            } else {
                                System.out.println("no");
                            }
                        }
                );

                editIcon.setOnMouseClicked(event -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChiTieu/update_chitieu.fxml"));
                    try {
                        stageManager.switchScene(fxmlLoader);
                        UpdateChiTieuController updateChiTieuController = fxmlLoader.getController();
                        ChiTieu chiTieu = getTableView().getItems().get(getIndex());
                        updateChiTieuController.initData(chiTieu.getId());
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });
            }

            protected void updateItem(Void aVoid, boolean b) {
                super.updateItem(aVoid, b);
                if (b) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
        ngayChiTieuColumn.setCellFactory(column -> {
            return new TableCell<ChiTieu, Date>() {
                private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if (b) {
                        setText(null);
                    } else {
                        ChiTieu chiTieu = getTableView().getItems().get(getIndex());
                        setText(sdf.format(chiTieu.getNgayChiTieu()));
                        System.out.println("Đã đi qua");
                    }
                }
            };
        });
        valueColumn.setCellFactory(column -> {
            return new TableCell<ChiTieu,Number >() {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                @Override
                protected void updateItem(Number aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if (b) {
                        setText(null);
                    } else {
                        ChiTieu chiTieu = getTableView().getItems().get(getIndex());
                        setText(currencyFormat.format(chiTieu.getSoTien()));
                    }
                }
            };
        });
        ObservableList<ChiTieu> chiTieuList = FXCollections.observableList(chiTieuService.getAllChiTieu());
        chiTieuTable.setItems(chiTieuList);
        calculateAndDisplayTotal(chiTieuList);
    }
    private void calculateAndDisplayTotal(ObservableList<ChiTieu> chiTieuList) {
        double total = chiTieuList.stream().mapToDouble(ChiTieu::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
    }
    public void switchChiTieu(ActionEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChiTieu/form_chitieu.fxml"));
            this.stageManager.switchScene(fxmlLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void searchExpense(ActionEvent actionEvent) {
        ObservableList<ChiTieu> chiTieuList = FXCollections.observableList(chiTieuService.getByChiTieu(searchField.getText()));
        double total = chiTieuList.stream().mapToDouble(ChiTieu::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
        chiTieuTable.setItems(chiTieuList);
    }
}
