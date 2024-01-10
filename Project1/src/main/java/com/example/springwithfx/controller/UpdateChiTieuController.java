package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.service.ChiTieuService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class UpdateChiTieuController extends AbstractController {
    @FXML
    public TextField loaiChiTieuField;
    public TextField matHangField;
    public TextField soTienField;
    public DatePicker ngayChiTieuPicker;
    public TextField ghiChuField;
    public TextField Id;

    private  StageManager stageManager;
    @Autowired
    private ChiTieuService chiTieuService;
    public UpdateChiTieuController(ChiTieuService chiTieuService, StageManager stageManager) {
        this.stageManager = stageManager;
        this.chiTieuService=chiTieuService;
    }
    public void initData(long id) {
        ChiTieu chiTieu = chiTieuService.getChiTieu(id);
        Id.setText(String.valueOf(chiTieu.getId()));
        loaiChiTieuField.setText(chiTieu.getLoaiChiTieu());
        matHangField.setText(chiTieu.getMatHang());
        soTienField.setText(String.valueOf(chiTieu.getSoTien()));
        ngayChiTieuPicker.setValue(LocalDate.of(
                chiTieu.getNgayChiTieu().toLocalDate().getYear(),
                chiTieu.getNgayChiTieu().toLocalDate().getMonthValue(),
                chiTieu.getNgayChiTieu().toLocalDate().getDayOfMonth()
        ));
        ghiChuField.setText(chiTieu.getGhiChu());
    }

    public void updateChiTieu() throws IOException {
        try {
            ChiTieu chiTieu = chiTieuService.getChiTieu( Long.parseLong(Id.getText()));
            chiTieu.setLoaiChiTieu(loaiChiTieuField.getText());
            chiTieu.setMatHang(matHangField.getText());
            chiTieu.setSoTien(Double.parseDouble(soTienField.getText()));
            chiTieu.setNgayChiTieu(java.sql.Date.valueOf(ngayChiTieuPicker.getValue()));
            chiTieu.setGhiChu(ghiChuField.getText());

            chiTieuService.saveChiTieu(chiTieu);
        } catch (Exception e) {
            AlertUnity.showErrorAlert("Lỗi", "Lỗi " + e.getMessage());
        }
        AlertUnity.showSuccessAlert("Thành công","Lưu thành công");
    }

}
