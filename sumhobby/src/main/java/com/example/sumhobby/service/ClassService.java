package com.example.sumhobby.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.ReviewRepository;

@Service
public class ClassService {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ReviewRepository revRepository;

	public List<ClassEntity> retrieve() {
		return classRepository.findAll();
	}

	public ClassEntity create(final ClassEntity entity) {
		return classRepository.save(entity);
	}

	public ClassEntity selectOne(final Integer classNum) {
		return classRepository.findById(classNum).get();
	}

	public void deleteOne(final ClassEntity entity) {
		classRepository.delete(entity);
	}

	public List<ClassEntity> getTop5RatedClassesByCategory() {
		List<String> categories = classRepository.findAllCategories();
		List<ClassEntity> topRatedClassesByCategory = new ArrayList<>();

		for (String category : categories) {
			List<ClassEntity> topRatedClasses = classRepository.findTop5ByClassCategoryOrderByClassRateDesc(category);
			topRatedClassesByCategory.addAll(topRatedClasses.stream().limit(5).collect(Collectors.toList()));
		}

		return topRatedClassesByCategory;
	}

	public List<ClassEntity> seletAllByUserRef(final UserEntity userEntity) {
		return classRepository.findByUserRef(userEntity);
	}
	
	public List<ClassEntity> selectBySearchKey(String searchKey) {
		return classRepository.findBySearchKey(searchKey);
	}

}
