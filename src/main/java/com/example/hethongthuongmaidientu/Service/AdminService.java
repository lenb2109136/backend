package com.example.hethongthuongmaidientu.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.TourRepository;

@Service
public class AdminService {

    @Autowired
    private TourRepository tourRepository;
    public Integer getTotalTrips() {
        return tourRepository.getTotalTrips();
    }
    public Integer getTotalEmployees() {
        return tourRepository.getTotalEmployees();
    }
    public Integer getTotalCustomers() {
        return tourRepository.getTotalCustomers();
    }
    public List<Map<String, Object>> getMonthlyRevenue() {
        return tourRepository.getMonthlyRevenue();
    }
    public List<Map<String, Object>> getTourTypeRevenue() {
        return tourRepository.getTourTypeRevenue();
    }
    public List<Map<String, Object>> getTicketSales() {
        return tourRepository.getTicketSales();
    }
    public Map<String, Object> getAdminMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // Tổng doanh thu (Earnings)
        Double earnings = tourRepository.getTotalEarnings();
        earnings = earnings != null ? earnings : 0.0;
        metrics.put("earnings", earnings / 1000000); // Chuyển sang triệu VND

        // Chi tiêu tháng này (Spend this month) - Giả định 50% doanh thu
        Double spend = earnings * 0.5;
        metrics.put("spend", spend / 1000000);

        // Doanh thu tháng hiện tại (Sales)
        LocalDateTime startOfMonth = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(2025, 3, 31, 23, 59, 59);
        Double sales = tourRepository.getMonthlySales(startOfMonth, endOfMonth);
        sales = sales != null ? sales : 0.0;
        metrics.put("sales", sales / 1000000);

        // Số dư (Balance)
        Double balance = earnings - spend;
        metrics.put("balance", balance / 1000000);

        // Nhiệm vụ mới (New Tasks)
        Integer newTasks = tourRepository.getNewTasks(LocalDateTime.now());
        metrics.put("newTasks", newTasks != null ? newTasks : 0);

        // Tổng số dự án (Total Projects)
        Integer totalProjects = tourRepository.getTotalProjects();
        metrics.put("totalProjects", totalProjects != null ? totalProjects : 0);

        return metrics;
    }
    
}