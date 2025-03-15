package com.example.hethongthuongmaidientu.model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.text.similarity.FuzzyScore;
import org.springframework.stereotype.Component;

@Component
public class ServiceSort {
	private static final FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());

	 public static int calculateTagSimilarity(String serviceTags, String searchKeywords) {
	        List<String> serviceTagList = Arrays.asList(serviceTags.toLowerCase().split("\\s*,\\s*"));
	        List<String> searchKeywordList = Arrays.asList(searchKeywords.toLowerCase().split("\\s*,\\s*"));

	        return searchKeywordList.stream()
	            .flatMap(keyword -> serviceTagList.stream().map(tag -> fuzzyScore.fuzzyScore(tag, keyword)))
	            .reduce(0, Integer::sum); 
	    }

	    public static List<DichVu> sortByTagRelevance(List<DichVu> services, String keyword) {
	        services.sort((s1, s2) -> {
	            int score1 = calculateTagSimilarity(s1.getTags(), keyword);
	            int score2 = calculateTagSimilarity(s2.getTags(), keyword);
	            return Integer.compare(score2, score1);
	        });
	        return services;
	    }
	    
	    public void sort(List<DichVu> services, String keyword) {
	    	List<DichVu> sortedServices = ServiceSort.sortByTagRelevance(services, keyword);
	    }
	    

}
