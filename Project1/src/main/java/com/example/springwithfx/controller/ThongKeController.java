package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.model.ThuNhap;
import com.example.springwithfx.service.ChiTieuService;
import com.example.springwithfx.service.ThuNhapService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@Slf4j
public class ThongKeController implements Initializable {

    private final StageManager stageManager;
    private final ThuNhapService thuNhapService;
    private final ChiTieuService chiTieuService;
    private Stage stage;
    @FXML
    private Label totalLabel;

    public ThongKeController(StageManager stageManager, ThuNhapService thuNhapService, ChiTieuService chiTieuService) {
        this.stageManager = stageManager;
        this.thuNhapService = thuNhapService;
        this.chiTieuService=chiTieuService;
    }

    public void commonSwitch(FXMLLoader fxmlLoader, ActionEvent event) throws IOException {
        stageManager.switchScene(stage, fxmlLoader, event);
    }

    public void switchThongKe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ThongKe/thongkeChiTiet.fxml"));
            commonSwitch(fxmlLoader, event);
        } catch (IOException e) {
            log.error("Error switching to ThuNhap ", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculateAndDisplayTotal();
    }

    private void calculateAndDisplayTotal() {
        ObservableList<ThuNhap> thuNhapList = FXCollections.observableList(thuNhapService.getAllThuNhap());
        ObservableList<ChiTieu> chiTieuList = FXCollections.observableList(chiTieuService.getAllChiTieu());
        double total = thuNhapList.stream().mapToDouble(ThuNhap::getSoTien).sum()-chiTieuList.stream().mapToDouble(ChiTieu::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
    }
}
