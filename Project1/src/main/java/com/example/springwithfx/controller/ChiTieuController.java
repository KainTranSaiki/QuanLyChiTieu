package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.service.ChiTieuService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
@Slf4j
public class ChiTieuController extends AbstractController {

    private Stage stage;
    private Scene scene;

    private final StageManager stageManager;
    private final ChiTieuService chiTieuService;
    private ChiTieu chiTieu = new ChiTieu();

    @FXML
    public TextField expenseNameField;
    public DatePicker expenseDatePicker;
    public TextField expenseAmountField;
    public TextField expenseCategoryField;

    public ChiTieuController(StageManager stageManager, ChiTieuService chiTieuService) {
        this.stageManager = stageManager;
        this.chiTieuService = chiTieuService;
    }

    public void getChiTieu() {
        chiTieu.setLoaiChiTieu(expenseNameField.getText());
        chiTieu.setNgayChiTieu(java.sql.Date.valueOf(expenseDatePicker.getValue()));
        chiTieu.setSoTien(Double.parseDouble(expenseAmountField.getText()));
        chiTieu.setMatHang(expenseCategoryField.getText());
        chiTieu.setGhiChu(expenseCategoryField.getText());
    }

    public void addExpense() throws IOException {
        try {
            getChiTieu();
            chiTieuService.saveChiTieu(chiTieu);
            AlertUnity.showInformationAlert("Thành công", "Thành công ");
            clearFormFields();
        } catch (Exception e) {
            log.error("Error adding expense", e);
            AlertUnity.showErrorAlert("Lỗi", "Xảy ra lỗi: " + e.getMessage());
        }
    }

    private void clearFormFields() {
        expenseNameField.clear();
        expenseDatePicker.setValue(null);
        expenseAmountField.clear();
        expenseCategoryField.clear();
    }

}
