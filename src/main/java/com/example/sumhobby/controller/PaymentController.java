package com.example.sumhobby.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.PaymentDTO;
import com.example.sumhobby.dto.PaymentRespDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.PaymentRespEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.PaymentService;
import com.example.sumhobby.service.UserService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/checkout")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
    @GetMapping(value = "/success")
    public RedirectView paymentResult(
            Model model,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey
            ) throws Exception {
    	PaymentRespDTO paymentRespDTO = new PaymentRespDTO();
    	paymentRespDTO.setAmount(amount);
    	paymentRespDTO.setOrderId(orderId);
    	paymentRespDTO.setPaymentKey(paymentKey);

        String secretKey = "test_sk_4vZnjEJeQVxXaL051vbVPmOoBN0k:";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        
        
        
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        String message = connection.getResponseMessage();
        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;
        model.addAttribute("isSuccess", isSuccess);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        model.addAttribute("responseStr", jsonObject.toJSONString());
        System.out.println(jsonObject.toJSONString());
        System.out.println(authorizations);

        model.addAttribute("method", (String) jsonObject.get("method"));
        model.addAttribute("orderName", (String) jsonObject.get("orderName"));
        paymentRespDTO.setOrderName((String) jsonObject.get("orderName"));
        paymentRespDTO.setRequestedAt((String) jsonObject.get("requestedAt"));
        paymentRespDTO.setIsSuccessful(isSuccess);
        
        if (((String) jsonObject.get("method")) != null) {
            if (((String) jsonObject.get("method")).equals("카드")) {
                model.addAttribute("cardNumber", (String) ((JSONObject) jsonObject.get("card")).get("number"));
            } else if (((String) jsonObject.get("method")).equals("가상계좌")) {
                model.addAttribute("accountNumber", (String) ((JSONObject) jsonObject.get("virtualAccount")).get("accountNumber"));
            } else if (((String) jsonObject.get("method")).equals("계좌이체")) {
                model.addAttribute("bank", (String) ((JSONObject) jsonObject.get("transfer")).get("bank"));
            } else if (((String) jsonObject.get("method")).equals("휴대폰")) {
                model.addAttribute("customerMobilePhone", (String) ((JSONObject) jsonObject.get("mobilePhone")).get("customerMobilePhone"));
            }
        } else {
            model.addAttribute("code", (String) jsonObject.get("code"));
            model.addAttribute("message", (String) jsonObject.get("message"));
        }
        
//        Map<String, List<String>> properties = connection.getRequestProperties();
        System.out.println(model.toString());
        System.out.println(code);
        System.out.println(message);
        System.out.println(paymentRespDTO);
//        System.out.println(properties.toString());
  
        PaymentRespEntity entity = PaymentRespDTO.toEntity(paymentRespDTO);
        service.create(entity);
//        HttpResponse<String> response = HttpClient.newHttpClient().send(, HttpResponse.BodyHandlers.ofString());
        
		RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://app.sumhobby.com/success?amount=" + amount+"&orderId=" + orderId+"&paymentKey="+paymentKey);
        return redirectView;
        
    }

    @GetMapping(value = "/fail")
    public ResponseEntity<?> paymentResult(
            Model model,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "code") Integer code
    ) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return ResponseEntity.ok().body("fail");
    }
    
    @PostMapping
    public ResponseEntity<?> createPayment(@AuthenticationPrincipal String userTk, @RequestBody PaymentDTO dto) {
    	System.out.println(dto.toString());
    	dto.setUserId(userService.selectOne(userTk).getUserId());
    	PaymentEntity entity = service.toEntity(dto);
    	entity.setPayDate(Timestamp.valueOf(LocalDateTime.now()));
    	List<PaymentEntity> entities = service.create(entity);
    	List<PaymentDTO> dtos = entities.stream().map(PaymentDTO::new).collect(Collectors.toList());
    	ResponseDTO<PaymentDTO> response = ResponseDTO.<PaymentDTO>builder().data(dtos).build();
    	
    	return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/result")
    public ResponseEntity<?> getPayment(@AuthenticationPrincipal String userTk, @RequestBody PaymentRespDTO dto){
    	
    	List<PaymentEntity> entities = service.findPayment(dto.getOrderId());
    	List<PaymentDTO> dtos = entities.stream().map(PaymentDTO::new).collect(Collectors.toList());
    	for (int i = 0; i < dtos.size(); i++) {
			ClassDTO classDTO = new ClassDTO(service.classRetrieve(dtos.get(i).getClassNum()).get());
			dtos.get(i).setClassPrice(classDTO.getClassPrice());
    	}
    	ResponseDTO<PaymentDTO> response = ResponseDTO.<PaymentDTO>builder().data(dtos).build();
    	System.out.println(dtos.toString());
    	return ResponseEntity.ok().body(response);
    }
    
    @PatchMapping
    public ResponseEntity<?> checkPayment(@RequestBody ClassDTO classDTO, @AuthenticationPrincipal String userTk){
    	UserEntity userEntity = userService.selectOne(userTk);
    	ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());
    	PaymentEntity paymentEntity = service.selectByClassRefAndUserRef(classEntity, userEntity);
    	if(paymentEntity != null) {
    		ClassEntity responseEntity = paymentEntity.getClassRef();
    		ClassDTO responseDTO = new ClassDTO(responseEntity);
    		return ResponseEntity.ok().body(responseDTO);
    	}else {
			return ResponseEntity.ok().body(null);
		}
    }
    
    

}