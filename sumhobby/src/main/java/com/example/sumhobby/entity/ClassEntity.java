package com.example.sumhobby.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class")
public class ClassEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer classNum;
	
	@Column(nullable = false)
	private String classDetail, classCategory;

	@Column(unique = true,nullable = false)
	private String className;
	
	private String classImg;
	
	@ColumnDefault("0.0")
	@Builder.Default
	private double classRate = 0.0;
	
	@Column(nullable = false)
	private int classPrice;
	
	private Timestamp classSetDate, classLastDate;
	
	@ManyToOne
	@JoinColumn(name = "userTk", referencedColumnName = "userTk")
	private UserEntity userRef;

	@OneToMany(mappedBy = "classRef")
	@EqualsAndHashCode.Exclude
	private List<LectureEntity> lectures;
	
	@OneToMany(mappedBy = "classRef")
	@EqualsAndHashCode.Exclude
	private List<QuestionEntity> questions;

	@OneToMany(mappedBy = "classRef")
	@EqualsAndHashCode.Exclude
	private List<ReviewEntity> reviews;
	
	@OneToMany(mappedBy = "classRef", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<CartEntity> carts;
	
	@OneToMany(mappedBy = "classRef")
	@EqualsAndHashCode.Exclude
	private List<PaymentEntity> payments;
}