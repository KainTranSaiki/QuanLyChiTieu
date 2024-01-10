package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ThuNhap;
import com.example.springwithfx.service.ThuNhapService;
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
public class ThuNhapController extends AbstractController{

    private Stage stage;
    private Scene scene;

    private final StageManager stageManager;
    private final ThuNhapService thuNhapService;

    // Replace the following line with your actual ThuNhap model class
    private ThuNhap thuNhap = new ThuNhap();

    @FXML
    public TextField incomeTypeField;
    public DatePicker incomeDatePicker;
    public TextField incomeAmountField;
    public TextField incomeNoteField;

    public ThuNhapController(StageManager stageManager, ThuNhapService thuNhapService) {
        this.stageManager = stageManager;
        this.thuNhapService = thuNhapService;
    }

    public void getThuNhap() {
        thuNhap.setLoaiThuNhap(incomeTypeField.getText());
        thuNhap.setNgayThuNhap(java.sql.Date.valueOf(incomeDatePicker.getValue()));
        thuNhap.setSoTien(Double.parseDouble(incomeAmountField.getText()));
        thuNhap.setGhiChu(incomeNoteField.getText());
    }

    public void addIncome() throws IOException {
        try {
            getThuNhap();
            thuNhapService.saveThuNhap(thuNhap);
            AlertUnity.showInformationAlert("Thành công", "Thành công");
            clearFormFields();
        } catch (Exception e) {
            log.error("Error adding income", e);
            AlertUnity.showErrorAlert("Lỗi", "Xảy ra lỗi: " + e.getMessage());
        }
    }
    private void clearFormFields() {
        incomeTypeField.clear();
        incomeDatePicker.setValue(null);
        incomeAmountField.clear();
        incomeNoteField.clear();
    }
    public void switchThuNhap(ActionEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ThuNhap/form_thunhap.fxml"));
            this.stageManager.switchScene(fxmlLoader,event,this.pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
