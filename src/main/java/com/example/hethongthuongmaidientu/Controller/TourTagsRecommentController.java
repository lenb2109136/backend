package com.example.hethongthuongmaidientu.Controller;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.model.TourSort;
import com.example.hethongthuongmaidientu.repository.TourRepository;

@RestController
@RequestMapping("/tourtagsrecomment")
public class TourTagsRecommentController {
	@Autowired
	private TourRepository tourRepository;
	@Autowired
	private TourSort tourSort;
	
	private String normalizeText(String text) {
	    text = text.toLowerCase().trim();
	    text = Normalizer.normalize(text, Normalizer.Form.NFD);
	    return text.replaceAll("[^\\p{ASCII}]", ""); 
	}
	private boolean isSimilar(String tag, String keyword) {
	    String normalizedTag = normalizeText(tag); 
	    return normalizedTag.equals(keyword) || normalizedTag.contains(keyword);
	}


	@GetMapping("/getthongthuong")
	public ResponseEntity<Response> getThongThuong(@RequestParam("tags") String tags){
		 String tagssum = tourRepository.getTags();

		    List<String> tagList = Arrays.stream(tagssum.split(","))
		            .map(String::trim)
		            .filter(s -> !s.isEmpty())
		            .collect(Collectors.toList());
		    String normalizedTag = normalizeText(tags);

		    List<String> resultTags = tagList.stream()
		            .filter(tag -> isSimilar(tag, normalizedTag))
		            .collect(Collectors.toList());

		    return new ResponseEntity(new Response(HttpStatus.OK,"ok", resultTags),HttpStatus.OK);
	}
	@GetMapping("/getrecomment")
	public ResponseEntity<Response> getTagsReCommend(@RequestParam("tags") String tags) {
	    List<Tour> t = tourRepository.findAll();
	    tourSort.sort(t, tags);
	    String allTags = t.stream()
	            .map(Tour::getTags)
	            .filter(Objects::nonNull)
	            .collect(Collectors.joining(","));
	    Set<String> a1 = new LinkedHashSet<>();
	    for (String tag : allTags.split(",")) {
	        a1.add(tag.trim());
	    }
	    Set<String> a2 = new HashSet<>();
	    for (String tag : tags.split(",")) {
	        a2.add(tag.trim());
	    }
	    a1.removeAll(a2);
	    List<String> result = new ArrayList<>(a1);
	    if (result.size() > 10) {
	        result = result.subList(0, 10);
	    }

	    return new ResponseEntity<>(new Response(HttpStatus.OK, null, result), HttpStatus.OK);
	}

	
}
