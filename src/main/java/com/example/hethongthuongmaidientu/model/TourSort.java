package com.example.hethongthuongmaidientu.model;

import org.apache.commons.text.similarity.FuzzyScore;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class TourSort {
    private static final FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());

    public static int calculateTagSimilarity(String tourTags, String searchKeywords) {
        List<String> tourTagList = Arrays.asList(tourTags.toLowerCase().split("\\s*,\\s*"));
        List<String> searchKeywordList = Arrays.asList(searchKeywords.toLowerCase().split("\\s*,\\s*"));

        return searchKeywordList.stream()
                .flatMap(keyword -> tourTagList.stream().map(tag -> fuzzyScore.fuzzyScore(tag, keyword)))
                .reduce(0, Integer::sum);
    }

    public static List<Tour> sortByTagRelevance(List<Tour> tours, String keyword) {
        return tours.stream()
                .sorted((t1, t2) -> {
                    int score1 = calculateTagSimilarity(t1.getTags(), keyword);
                    int score2 = calculateTagSimilarity(t2.getTags(), keyword);
                    return Integer.compare(score2, score1); 
                })
                .collect(Collectors.toList());
    }

    public void sort(List<Tour> tours, String keyword) {
        List<Tour> sortedTours = TourSort.sortByTagRelevance(tours, keyword);
        tours.clear();
        tours.addAll(sortedTours);
    }

}
