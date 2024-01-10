package com.example.springwithfx.service;

import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.repository.ChiTieuRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChiTieuService {

    private final ChiTieuRepository chiTieuRepository;

    public ChiTieuService(ChiTieuRepository chiTieuRepository) {
        this.chiTieuRepository = chiTieuRepository;
    }

    public void saveChiTieu(ChiTieu chiTieu) {
        chiTieuRepository.save(chiTieu);
    }
    public List<ChiTieu> getAllChiTieu() {
        return chiTieuRepository.findAll();
    }

    public void deleteChiTieu(long id) {
        chiTieuRepository.deleteById(id);
    }

    public ChiTieu getChiTieu(long id) {
        return chiTieuRepository.findById(id).orElse(null);
    }

    public List<ChiTieu> getByLoaiChiTieu(String loaiChiTieu) {
        return chiTieuRepository.findByLoaiChiTieu(loaiChiTieu);
    }
    public Set<String> getAllLoaiChiTieu() {
        List<ChiTieu> allChiTieuList = chiTieuRepository.findAll();
        return allChiTieuList.stream()
                .map(ChiTieu::getLoaiChiTieu)
                .collect(Collectors.toSet());
    }
    public List<ChiTieu> getBySoTienGreaterThanEqual(Double soTien) {
        return chiTieuRepository.findBySoTienGreaterThanEqual(soTien);
    }
    public List<ChiTieu> getByChiTieu(String matHang) {
        return chiTieuRepository.findByMatHang(matHang);
    }
    public List<ChiTieu> getByNgayChiTieuBetween(Date startDate, Date endDate) {
        return chiTieuRepository.findByNgayChiTieuBetween(startDate, endDate);
    }

    public ChiTieu getByGhiChu(String ghiChu) {
        return chiTieuRepository.findByGhiChu(ghiChu);
    }

    public void deleteByLoaiChiTieu(String loaiChiTieu) {
        chiTieuRepository.deleteByLoaiChiTieu(loaiChiTieu);
    }

}
