package com.example.springwithfx.repository;

import com.example.springwithfx.model.ChiTieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ChiTieuRepository extends JpaRepository<ChiTieu, Long> {

    List<ChiTieu> findByLoaiChiTieu(String loaiChiTieu);

    List<ChiTieu> findBySoTienGreaterThanEqual(Double soTien);

    List<ChiTieu> findByNgayChiTieuBetween(Date startDate, Date endDate);
    ChiTieu findByGhiChu(String ghiChu);
    List<ChiTieu> findByMatHang(String matHang);
    @Transactional
    void deleteByLoaiChiTieu(String loaiChiTieu);
}