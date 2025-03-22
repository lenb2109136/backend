package com.example.hethongthuongmaidientu.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.PhiDichVu;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.VE;
import com.example.hethongthuongmaidientu.repository.DichVuRepository;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;
import com.example.hethongthuongmaidientu.repository.PhiDichVuRepository;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;
import com.example.hethongthuongmaidientu.repository.VeRepository;

import DTO.ThongTinDatVe;
import DTO.VeDTO;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VeService {
    @Autowired
    private VeRepository veRepository;

    @Autowired
    private DichVuRepository dichVuRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private PhiDichVuRepository phiDichVuRepository;

    @Autowired
    private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;

    public List<VE> getAllBookings() {
        return veRepository.findAll();
    }

    @Transactional
    public void deleteBooking(int id) {
        if (!veRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy vé với ID: " + id);
        }
        veRepository.deleteById(id);
    }

    @Transactional
    public VE updateBooking(int id, VE updatedVe) {
        VE existingVe = veRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vé với ID: " + id));

        // Cập nhật các trường
        existingVe.setGia(updatedVe.getGia());
        existingVe.setNgayDat(updatedVe.getNgayDat());
        existingVe.setKhachHang(updatedVe.getKhachHang());
        existingVe.setThoiGianKhoiHanh(updatedVe.getThoiGianKhoiHanh());

        return veRepository.save(existingVe);
    }

    @Transactional
    public VE themVeMoi(ThongTinDatVe t) {
        VE vv = new VE();
        KhachHang kh = khachHangRepository.findById(t.getIdkh())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng cần đặt"));
        for (int kk = 0; kk < t.getInfove().size(); kk++) {
            VeDTO data = t.getInfove().get(kk);
            ThoiGianKhoiHanh tt = thoiGianKhoiHanhRepository.findById(data.getIdtgkh())
                    .orElseThrow(() -> new EntityNotFoundException("Vui lòng chọn thời gian khởi hành cho tour"));
            VE v = new VE();
            v.setKhachHang(kh);
            v.setNgayDat(LocalDateTime.now());
            v.setThoiGianKhoiHanh(tt);
            v.setGia(tt.getGia());
            tt.getGiaUuDai().forEach((da) -> {
                if (!LocalDateTime.now().isBefore(da.getNgayGioApDung()) && !LocalDateTime.now().isAfter(da.getNgayKetThuc())) {
                    v.setGia(da.getGia());
                }
            });
            veRepository.save(v);
            System.out.println("thông tin id vé: " + v.getId());
            vv = v;
            data.getDsdv().forEach(d -> {
                DichVu d1 = dichVuRepository.findById(d + 1)
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy dịch vụ cần đăng ký"));
                System.out.println("id dịch vụ : " + d1.getId());
                System.out.println("Tên dịch vụ : " + d1.getTen());
                PhiDichVu pp = new PhiDichVu();
                pp.setDichVu(d1);
                System.out.println(d1.getGia());
                pp.setGia(d1.getGia());
                pp.setVe(v);
                phiDichVuRepository.save(pp);
            });
        }
        return vv;
    }
}