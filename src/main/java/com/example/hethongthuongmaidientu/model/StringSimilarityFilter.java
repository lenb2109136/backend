package com.example.hethongthuongmaidientu.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;

public class StringSimilarityFilter {
	  public static List<Map<String, Object>> filterBySimilarity(String input, List<Map<String, Object>> list) {
	        if (input == null || list == null) {
	            return Collections.emptyList();
	        }

	        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

	        return list.stream()
	                .filter(map -> {
	                    if (map.containsKey("T_TEN")) {
	                        String name = map.get("T_TEN").toString();
	                        double score = similarity.apply(input, name);
	                        return score >= 0.7; 
	                    }
	                    return false;
	                })
	                .collect(Collectors.toList());
	    }
}
