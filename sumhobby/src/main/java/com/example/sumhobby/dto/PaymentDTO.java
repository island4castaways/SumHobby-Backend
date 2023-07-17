package com.example.sumhobby.dto;

import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;
import com.example.sumhobby.util.Util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	
	private String userId, className, orderId, payDate;
	private Integer classPrice, classNum, paymentNum;
	
	public PaymentDTO(final PaymentEntity entity) {
		this.userId = entity.getUserRef().getUserId();
		this.classNum = entity.getClassRef().getClassNum();
		this.className = entity.getClassRef().getClassName();
		this.classPrice = entity.getClassRef().getClassPrice();
		this.paymentNum = entity.getPaymentNum();
		this.payDate = Util.timestampToString(entity.getPayDate());
	}
	
	public static PaymentEntity toEntity(final PaymentDTO dto, ClassRepository classRepository, UserRepository userRepository) {
		return PaymentEntity.builder()
				.paymentNum(dto.getPaymentNum())
				.classRef(classRepository.findById(dto.classNum).get())
				.userRef(userRepository.findByUserId(dto.userId))
				.orderId(dto.getOrderId())
				.build();
	}

}