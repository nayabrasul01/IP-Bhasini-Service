package com.esic.checklist.controller;

import com.esic.checklist.annotations.Traceable;
import com.esic.checklist.dto.ResponseDto;
import com.esic.checklist.model.Response;
import com.esic.checklist.service.ResponseService;
import com.esic.checklist.utility.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ResponseController {

	@Autowired
    private final ResponseService responseService;
    
    @Autowired
    private final JwtUtil jwtUtil;

    @Traceable
    @PostMapping
    public ResponseEntity<String> saveResponse(@RequestBody ResponseDto dto, HttpServletRequest request) {
    	String authHeader = request.getHeader("Authorization");
    	String token = authHeader.substring(7); // Remove "Bearer "
    	String createdBy = jwtUtil.getClaims(token).getSubject();
    	dto.setCreatedBy(createdBy);
    	
        responseService.saveOrUpdateResponse(dto);
        return ResponseEntity.ok("Saved");
    }

    @Traceable
    @GetMapping("/user/{userId}/questionnaire/{qid}")
    public List<Response> getResponses(@PathVariable String userId, @PathVariable Long qid) {
        return responseService.getResponses(userId, qid);
    }
}
