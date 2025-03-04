package com.example.hethongthuongmaidientu.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/datasource")
public class ResourceStatic {
	@Value("${app.nameSource}")
	private String resource;
	@GetMapping("/{name}")
	public void returnDataSource(@PathVariable("name") String name, HttpServletRequest rq,HttpServletResponse ep) {
		System.out.println(name);
		File f= new File("C://Users//ADMIN//Documents//A- CT263//images2//"+name);
		System.out.println("tên đường dẫn file: "+f.getName());
		try {
			OutputStream o= ep.getOutputStream();
			InputStream ip= new FileInputStream(f);
			o.write(ip.readAllBytes());
			o.flush();
			ip.close();
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}