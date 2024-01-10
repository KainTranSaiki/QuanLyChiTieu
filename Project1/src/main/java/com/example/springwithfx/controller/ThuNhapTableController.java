package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.model.ThuNhap;
import com.example.springwithfx.service.ThuNhapService;
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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ThuNhapTableController extends AbstractController implements Initializable {
    private final StageManager stageManager;
    private final ThuNhapService thuNhapService;
    private Stage stage;
    @FXML
    public TableView<ThuNhap> thuNhapTable;
    @FXML
    private Label totalLabel;
    public TableColumn valueColumn;
    public TableColumn actionColumn;
    public TableColumn ngayThuNhapColumn;
    public TextField searchField;

    public ThuNhapTableController(StageManager stageManager, ThuNhapService thuNhapService) {
        this.stageManager = stageManager;
        this.thuNhapService = thuNhapService;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ThuNhapTableController controller = this;
        actionColumn.setCellFactory(param -> new TableCell<ThuNhap, Void>() {
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
                                ThuNhap thuNhap = getTableView().getItems().get(getIndex());
                                thuNhapService.deleteThuNhap(thuNhap.getId());
                                thuNhapTable.getItems().remove(getIndex());
                                calculateAndDisplayTotal(thuNhapTable.getItems());
                            } else {
                                System.out.println("no");
                            }
                        }
                );

                editIcon.setOnMouseClicked(event -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ThuNhap/update_thunhap.fxml"));
                    try {
                        stageManager.switchScene(fxmlLoader);
                        UpdateThuNhapController updateThuNhapController = fxmlLoader.getController();
                        ThuNhap thuNhap = getTableView().getItems().get(getIndex());
                        updateThuNhapController.initData(thuNhap.getId());
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
        ngayThuNhapColumn.setCellFactory(column -> {
            return new TableCell<ThuNhap, Date>() {
                private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if (b) {
                        setText(null);
                    } else {
                        ThuNhap thuNhap = getTableView().getItems().get(getIndex());
                        setText(sdf.format(thuNhap.getNgayThuNhap()));
                        System.out.println("Đã đi qua");
                    }
                }
            };
        });
        valueColumn.setCellFactory(column -> {
            return new TableCell<ThuNhap,Number >() {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                @Override
                protected void updateItem(Number aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if (b) {
                        setText(null);
                    } else {
                        ThuNhap thuNhap = getTableView().getItems().get(getIndex());
                        setText(currencyFormat.format(thuNhap.getSoTien()));
                    }
                }
            };
        });
        ObservableList<ThuNhap> thuNhapList = FXCollections.observableList(thuNhapService.getAllThuNhap());
        thuNhapTable.setItems(thuNhapList);
        calculateAndDisplayTotal(thuNhapList);
    }

    private void calculateAndDisplayTotal(ObservableList<ThuNhap> thuNhapList) {
        double total = thuNhapList.stream().mapToDouble(ThuNhap::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
    }

    public void searchIncome(ActionEvent actionEvent) {
        ObservableList<ThuNhap> thuNhapList = FXCollections.observableList(thuNhapService.getByThuNhap(searchField.getText()));
        double total = thuNhapList.stream().mapToDouble(ThuNhap::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
        thuNhapTable.setItems(thuNhapList);
    }
    public void switchThuNhap(ActionEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ThuNhap/form_thunhap.fxml"));
            this.stageManager.switchScene(fxmlLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
