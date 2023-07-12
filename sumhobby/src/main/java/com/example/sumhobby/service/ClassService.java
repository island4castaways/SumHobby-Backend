package com.example.sumhobby.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.repository.ClassRepository;

@Service
public class ClassService {
	
	@Autowired
	private ClassRepository classRepository;
	
	public List<ClassEntity> retrieve(){
		return classRepository.findAll();
	}
	
	public List<ClassDTO> getTopRatedClassesByCategory() {
	    List<String> categories = classRepository.findAllCategories();
	    List<ClassDTO> topRatedClassesByCategory = new ArrayList<>();

	    for (String category : categories) {
	        List<ClassEntity> topRatedClasses = classRepository.findTop5ByClassCategoryOrderByClassRateDesc(category);
	        topRatedClassesByCategory.addAll(topRatedClasses.stream().map(ClassDTO::new).collect(Collectors.toList()));
	    }

	    return topRatedClassesByCategory;
	}
}
