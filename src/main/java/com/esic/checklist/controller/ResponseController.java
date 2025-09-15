package com.esic.checklist.controller;

import com.esic.checklist.dto.ResponseDto;
import com.esic.checklist.model.Response;
import com.esic.checklist.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<String> saveResponse(@RequestBody ResponseDto dto) {
        responseService.saveResponse(dto);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/user/{userId}/questionnaire/{qid}")
    public List<Response> getResponses(@PathVariable Long userId, @PathVariable Long qid) {
        return responseService.getResponses(userId, qid);
    }
}
