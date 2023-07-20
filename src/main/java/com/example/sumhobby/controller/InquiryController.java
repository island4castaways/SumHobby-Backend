package com.example.sumhobby.controller;

import com.example.sumhobby.dto.InquiryDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.service.InquiryService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    //글 작성
    @PostMapping("/write")
    public ResponseEntity<ResponseDTO<InquiryDTO>> writeInquiry(@RequestBody InquiryDTO inquiryDTO) {
        try {
            InquiryEntity entity = InquiryDTO.toEntity(inquiryDTO);
            InquiryEntity savedEntity = inquiryService.create(entity);
            InquiryDTO savedDTO = new InquiryDTO(savedEntity);
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data((List<InquiryDTO>) savedDTO).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String msg = e.getMessage();
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().error(msg).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    //글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<InquiryDTO>> getInquiries() {
        try {
            List<InquiryEntity> inquiries = inquiryService.selectAll();
            List<InquiryDTO> dtos = inquiries.stream().map(InquiryDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
