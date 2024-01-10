package com.example.springwithfx.service;

import com.example.springwithfx.model.ChiTieu;
import com.example.springwithfx.model.ThuNhap;
import com.example.springwithfx.repository.ThuNhapRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ThuNhapService {

    private final ThuNhapRepository thuNhapRepository;

    public ThuNhapService(ThuNhapRepository thuNhapRepository) {
        this.thuNhapRepository = thuNhapRepository;
    }
    public void saveThuNhap(ThuNhap thuNhap) {
        thuNhapRepository.save(thuNhap);
    }
    public List<ThuNhap> getByThuNhap(String loaiThuNhap) {
        return thuNhapRepository.findByLoaiThuNhap(loaiThuNhap);
    }
    public List<ThuNhap> getAllThuNhap() {
        return thuNhapRepository.findAll();
    }

    public void deleteThuNhap(long id) {
        thuNhapRepository.deleteById(id);
    }

    public ThuNhap getThuNhap(long id) {
        return thuNhapRepository.findById(id).orElse(null);
    }

    public List<ThuNhap> getByLoaiThuNhap(String loaiThuNhap) {
        return thuNhapRepository.findByLoaiThuNhap(loaiThuNhap);
    }
    public List<ThuNhap> getByNgayThuNhapBetween(Date startDate, Date endDate) {
        return thuNhapRepository.findByNgayThuNhapBetween(startDate, endDate);
    }
}
