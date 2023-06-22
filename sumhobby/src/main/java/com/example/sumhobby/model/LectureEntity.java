package com.example.sumhobby.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture")
public class LectureEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(nullable = false)
		private int lectureNum;
		
		@Column(nullable = false)
		private String lectureTitle;
		
		@Column(nullable = false)
		private String lectureDetail;
		
		@Column(nullable = false)
		private String lectureUrl;
		
		@ManyToOne
		@JoinColumn(name = "", referencedColumnName = "classNum")
		private ClassEntity classEntity;
	}


