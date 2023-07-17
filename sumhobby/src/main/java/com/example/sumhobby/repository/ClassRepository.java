package com.example.sumhobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

	List<ClassEntity> findAllByclassCategory(String classCategory);
	
	@Query("SELECT c FROM ClassEntity c WHERE c.classCategory = :category ORDER BY c.classRate DESC")
	   List<ClassEntity> findTop5ByClassCategoryOrderByClassRateDesc(@Param("category") String category);
	
	@Query("SELECT DISTINCT c.classCategory FROM ClassEntity c")
    List<String> findAllCategories();
	
	@Query("SELECT c FROM ClassEntity c WHERE c.className = :className")
	ClassEntity findByClassName(@Param("className") String className);
	
	
}
