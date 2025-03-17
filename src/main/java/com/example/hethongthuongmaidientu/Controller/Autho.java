package com.example.hethongthuongmaidientu.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Config.JWT;
import com.example.hethongthuongmaidientu.Service.KhachHangService;
import com.example.hethongthuongmaidientu.Service.NhanVienService;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.NHANVIEN;
import com.example.hethongthuongmaidientu.model.Response;

@RestController
@RequestMapping("/autho")
public class Autho {
	@Autowired
	private NhanVienService nhanVienService;
	
	@Autowired
	private KhachHangService khachHangService;
	
	@Autowired
	private JWT jsonWebToken;
	
	@PostMapping("/login")
	public ResponseEntity<Response> getTokkenNhanVien(@RequestParam(name = "sdt", required = false) String sdt, 
			@RequestParam(name = "pass", required = false) String pass
			) throws Exception{
		
		if(sdt==null||pass==null ||sdt=="" ||pass=="") throw new Exception("Vui lòng nhập đầy đủ thông tin");
		NHANVIEN nv= nhanVienService.findBySoDienThoai(sdt);
		KhachHang kh= khachHangService.findBySoDienThoai(sdt);
		
		if(nv==null&&kh==null) {
			throw new Exception("Thông tin đăng nhập không hợp lệ");
		}
		if(nv!=null&&nv.getMatKhau().equals(pass)==false) {
			throw new Exception("Thông tin đăng nhập không hợp lệ");
		}
		if(kh!=null&&kh.getMatKhau().equals(pass)==false) {
			throw new Exception("Thông tin đăng nhập không hợp lệ");
		}
		Map<String, Object> authoreturn= new HashMap<String, Object>();
		if(nv!=null) {
			authoreturn.put("token", jsonWebToken.createToken(nv.getSdt(),"nhanvien"));
			authoreturn.put("role", "nhanvien");
			authoreturn.put("ten", nv.getTen());
			authoreturn.put("sdt", nv.getSdt());
			authoreturn.put("id", nv.getId());
			NHANVIEN.idnv=nv.getId();
		}
		else {
			authoreturn.put("token", jsonWebToken.createToken(kh.getSoDienThoai(),"khachhang"));
			authoreturn.put("role", "khachhang");
			authoreturn.put("ten", kh.getTen());
			authoreturn.put("sdt", kh.getSoDienThoai());
			authoreturn.put("id", kh.getId());
			KhachHang.idkh=kh.getId();
		}
		 Response r= new Response();
		 r.setData(authoreturn);
		 r.setMessage("OK");
		 r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
}
