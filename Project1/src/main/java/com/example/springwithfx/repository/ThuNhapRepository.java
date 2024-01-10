package com.example.springwithfx.repository;

import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.model.ThuNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ThuNhapRepository extends JpaRepository<ThuNhap, Long> {
    List<ThuNhap> findByLoaiThuNhap(String loaiThuNhap);

    List<ThuNhap> findBySoTienGreaterThanEqual(Double soTien);

    List<ThuNhap> findByNgayThuNhapBetween(Date startDate, Date endDate);
    ThuNhap findByGhiChu(String ghiChu);
    @Transactional
    void deleteByLoaiThuNhap(String loaiThuNhap);

}
