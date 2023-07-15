package com.example.sumhobby.dto;

import com.example.sumhobby.entity.PaymentRespEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRespDTO {
	
	private String orderId, paymentKey, orderName, requestedAt;
	private Integer amount;
	private Boolean isSuccessful;
	
	public PaymentRespDTO(final PaymentRespEntity entity) {
		this.orderId = entity.getOrderId();
		this.paymentKey = entity.getPaymentKey();
		this.amount = entity.getAmount();
		this.orderName = entity.getOrderName();
		this.requestedAt = entity.getRequestedAt();
		this.isSuccessful = entity.getIsSuccessful();
	}
	
	public static PaymentRespEntity toEntity(final PaymentRespDTO dto) {
		return PaymentRespEntity.builder()
				.orderId(dto.getOrderId())
				.amount(dto.getAmount())
				.paymentKey(dto.getPaymentKey())
				.orderName(dto.getOrderName())
				.requestedAt(dto.getRequestedAt())
				.isSuccessful(dto.getIsSuccessful())
				.build();
	}

}
