package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ThuNhap;
import com.example.springwithfx.service.ThuNhapService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateThuNhapController extends AbstractController {
    @FXML
    public TextField loaiThuNhapField;
    public TextField soTienField;
    public DatePicker ngayThuNhapPicker;
    public TextField ghiChuField;
    public TextField Id;
    private  StageManager stageManager;
    @Autowired
    private ThuNhapService thuNhapService;
    public UpdateThuNhapController(ThuNhapService thuNhapService, StageManager stageManager) {
        this.stageManager = stageManager;
        this.thuNhapService=thuNhapService;
    }
    public void initData(long id) {
        ThuNhap thuNhap = thuNhapService.getThuNhap(id);
        Id.setText(String.valueOf(thuNhap.getId()));
        loaiThuNhapField.setText(thuNhap.getLoaiThuNhap());
        soTienField.setText(String.valueOf(thuNhap.getSoTien()));
        ngayThuNhapPicker.setValue(thuNhap.getNgayThuNhap().toLocalDate());
        ghiChuField.setText(thuNhap.getGhiChu());
    }

    public void updateThuNhap() throws IOException {
        try {
            ThuNhap thuNhap = thuNhapService.getThuNhap( Long.parseLong(Id.getText()));
            thuNhap.setLoaiThuNhap(loaiThuNhapField.getText());
            thuNhap.setSoTien(Double.parseDouble(soTienField.getText()));
            thuNhap.setNgayThuNhap(java.sql.Date.valueOf(ngayThuNhapPicker.getValue()));
            thuNhap.setGhiChu(ghiChuField.getText());

            thuNhapService.saveThuNhap(thuNhap);
        } catch (Exception e) {
            AlertUnity.showErrorAlert("Lỗi", "Lỗi " + e.getMessage());
        }
        AlertUnity.showSuccessAlert("Thành công","Lưu thành công");
    }

}
