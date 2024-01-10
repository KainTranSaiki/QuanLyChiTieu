package com.example.springwithfx.controller;

import com.example.springwithfx.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {


	@Autowired
	private StageManager stageManager;

	@FXML
	private BorderPane borderPane;

	public void setChiTieu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ChiTieu/chitieuTable.fxml"));
		this.stageManager.switchScene(loader, event, borderPane);
	}

	public void setThuNhap(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ThuNhap/thunhapTable.fxml"));
		this.stageManager.switchScene(loader, event, borderPane);

	}

	public void setThongKe(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ThongKe/thongke.fxml"));
		this.stageManager.switchScene(loader);
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}

}
