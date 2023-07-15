package com.example.sumhobby.dto;

import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	
	private String userTk, className, orderId;
	private Integer classPrice, classNum, paymentNum;
	
	public PaymentDTO(final PaymentEntity entity) {
		this.classNum = entity.getClassRef().getClassNum();
		this.className = entity.getClassRef().getClassName();
		this.paymentNum = entity.getPaymentNum();
	}
	
	public static PaymentEntity toEntity(final PaymentDTO dto, ClassRepository classRepository, UserRepository userRepository) {
		return PaymentEntity.builder()
				.paymentNum(dto.getPaymentNum())
				.classRef(classRepository.findById(dto.classNum).get())
				.userRef(userRepository.findById(dto.userTk).get())
				.orderId(dto.getOrderId())
				.build();
	}

}