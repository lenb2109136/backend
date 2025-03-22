package com.example.hethongthuongmaidientu.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.text.Normalizer;
import java.util.*;


public class StringSimilarityFilter {
	
	   public static List<Map<Object, Object>> filterBySimilarity(String input, List<Map<Object, Object>> list) {
	        // Chuẩn hóa input để loại bỏ dấu
	        String normalizedInput = normalizeString(input);

	        list.sort((map1, map2) -> {
	            String tourName1 = normalizeString((String) map1.get("T_TEN"));
	            String tourName2 = normalizeString((String) map2.get("T_TEN"));

	            // Kiểm tra chứa input
	            boolean containsH1 = tourName1.contains(normalizedInput);
	            boolean containsH2 = tourName2.contains(normalizedInput);

	            // Nếu một trong hai chứa input thì ưu tiên hơn
	            if (containsH1 && !containsH2) return -1;
	            if (!containsH1 && containsH2) return 1;

	            // Nếu cả hai đều chứa hoặc không chứa, dùng khoảng cách Levenshtein theo tỷ lệ
	            LevenshteinDistance levenshtein = new LevenshteinDistance();
	            double distance1 = levenshtein.apply(normalizedInput, tourName1) / (double) tourName1.length();
	            double distance2 = levenshtein.apply(normalizedInput, tourName2) / (double) tourName2.length();

	            return Double.compare(distance1, distance2);
	        });

	        return list;
	    }

	    // Chuẩn hóa chuỗi (loại bỏ dấu)
	    private static String normalizeString(String str) {
	        return Normalizer.normalize(str, Normalizer.Form.NFD)
	                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	                .toLowerCase();
	    }

}
