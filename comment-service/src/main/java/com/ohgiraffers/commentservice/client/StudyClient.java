package com.ohgiraffers.commentservice.client;

import com.ohgiraffers.commentservice.dto.StudyStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "study-service", path = "/study-status")
public interface StudyClient {

    @GetMapping("/{roomId}")
    StudyStatusResponse getStudyStatus(@PathVariable("roomId") String roomId);
}


