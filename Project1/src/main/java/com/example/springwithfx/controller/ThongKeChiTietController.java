package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.service.ChiTieuService;
import com.example.springwithfx.service.ThuNhapService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ThongKeChiTietController extends AbstractController implements Initializable  {
    @FXML
    private AnchorPane thongkechitieu;
    @FXML
    private DatePicker DatePicker1;
    @FXML
    private Label totalLabel;
    @FXML
    private DatePicker DatePicker2;
    @FXML
    private PieChart thongkechitieuPieChart;
    private final StageManager stageManager;
    private final ChiTieuService chiTieuService;
    private final ThuNhapService thuNhapService;
    public ThongKeChiTietController(StageManager stageManager, ChiTieuService chiTieuService, ThuNhapService thuNhapService) {
        this.stageManager = stageManager;
        this.chiTieuService = chiTieuService;
        this.thuNhapService=thuNhapService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createChiTieuPieChart();
    }

    private void createChiTieuPieChart() {
        ObservableList<PieChart.Data> pieChartData = getChartData();
        thongkechitieuPieChart = new PieChart(pieChartData);
        thongkechitieuPieChart.setPrefSize(400, 600);



        thongkechitieu.getChildren().add(thongkechitieuPieChart);
    }
    private ObservableList<PieChart.Data> getChartData() {

        List<ChiTieu> chiTieuList = chiTieuService.getAllChiTieu();
        double total = chiTieuList.stream().mapToDouble(ChiTieu::getSoTien).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotal = currencyFormat.format(total);
        totalLabel.setText(String.format(formattedTotal));
        Map<String, Double> expenseByCategory = chiTieuList.stream()
                .collect(Collectors.groupingBy(ChiTieu::getLoaiChiTieu, Collectors.summingDouble(ChiTieu::getSoTien)));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        return pieChartData;
    }
    @FXML
    private void showChart() {
        ObservableList<PieChart.Data> pieChartData = getChartData();
        thongkechitieuPieChart.setData(pieChartData);
    }

    public void showChart(ActionEvent actionEvent) {
        try {
            Date startDate = java.sql.Date.valueOf(DatePicker1.getValue());
            Date endDate = java.sql.Date.valueOf(DatePicker2.getValue());
            if (startDate != null && endDate != null && !startDate.after(endDate)) {
                List<ChiTieu> chiTieuList = chiTieuService.getByNgayChiTieuBetween(startDate, endDate);
                double total = chiTieuList.stream().mapToDouble(ChiTieu::getSoTien).sum();
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedTotal = currencyFormat.format(total);
                totalLabel.setText(String.format(formattedTotal));
                Map<String, Double> expenseByCategory = chiTieuList.stream()
                        .collect(Collectors.groupingBy(ChiTieu::getLoaiChiTieu, Collectors.summingDouble(ChiTieu::getSoTien)));
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
                    pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                thongkechitieuPieChart.setData(pieChartData);
            } else {

                AlertUnity.showErrorAlert("Lỗi", "Mời nhập đầy đủ ngày tháng năm");
            }
        }
        catch(Exception e){
            AlertUnity.showErrorAlert("Lỗi","Lỗi"+e.getMessage());
        }
    }
}