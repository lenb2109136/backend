package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.Service.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/metrics")
    public ResponseEntity<Response> getAdminMetrics() {
        Map<String, Object> metrics = adminService.getAdminMetrics();

        Response response = new Response();
        response.setData(metrics);
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/total-earnings")
    public ResponseEntity<Response> getTotalEarnings() {
        Map<String, Object> metrics = adminService.getAdminMetrics();
        Double earnings = (Double) metrics.get("earnings") * 1000000; // Chuyển từ triệu VND về VND

        Response response = new Response();
        response.setData(Map.of("total", earnings));
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<Response> getMonthlySales() {
        Map<String, Object> metrics = adminService.getAdminMetrics();
        Double sales = (Double) metrics.get("sales") * 1000000; // Chuyển từ triệu VND về VND

        Response response = new Response();
        response.setData(Map.of("total", sales));
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/new-tasks")
    public ResponseEntity<Response> getNewTasks() {
        Map<String, Object> metrics = adminService.getAdminMetrics();
        Integer newTasks = (Integer) metrics.get("newTasks");

        Response response = new Response();
        response.setData(Map.of("count", newTasks));
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/total-tours")
    public ResponseEntity<Response> getTotalTours() {
        Map<String, Object> metrics = adminService.getAdminMetrics();
        Integer totalProjects = (Integer) metrics.get("totalProjects");

        Response response = new Response();
        response.setData(Map.of("count", totalProjects));
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/total-customers")
    public ResponseEntity<Response> getTotalCustomers() {
        Integer count = adminService.getTotalCustomers();
        Response response = new Response(HttpStatus.OK, "OK", Map.of("count", count));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/total-employees")
    public ResponseEntity<Response> getTotalEmployees() {
        Integer count = adminService.getTotalEmployees();
        Response response = new Response(HttpStatus.OK, "OK", Map.of("count", count));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/total-trips")
    public ResponseEntity<Response> getTotalTrips() {
        Integer count = adminService.getTotalTrips();
        Response response = new Response(HttpStatus.OK, "OK", Map.of("count", count));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/monthly-revenue")
    public ResponseEntity<Response> getMonthlyRevenue() {
        List<Map<String, Object>> revenue = adminService.getMonthlyRevenue();
        Response response = new Response(HttpStatus.OK, "OK", revenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/tour-type-revenue")
    public ResponseEntity<Response> getTourTypeRevenue() {
        List<Map<String, Object>> revenue = adminService.getTourTypeRevenue();
        Response response = new Response(HttpStatus.OK, "OK", revenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/ticket-sales")
    public ResponseEntity<Response> getTicketSales() {
        List<Map<String, Object>> tickets = adminService.getTicketSales();
        Response response = new Response(HttpStatus.OK, "OK", tickets);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}