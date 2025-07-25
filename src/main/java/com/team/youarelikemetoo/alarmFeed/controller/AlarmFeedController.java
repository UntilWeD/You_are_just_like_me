package com.team.youarelikemetoo.alarmFeed.controller;


import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.service.AlarmFeedService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/alarmFeed")
@RequiredArgsConstructor
public class AlarmFeedController {

    private final AlarmFeedService alarmFeedService;


    @Operation(summary = "알람 피드를 저장합니다.", description = "요청한 알람피드를 저장합니다.")
    @ApiResponse(
            responseCode = "200", description = "알람 피드 저장",
            content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    public ResponseEntity<?> getSaveAlarmFeedRequest(@RequestPart("data") AlarmFeedDTO dto,
                                                     @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails){
        AlarmFeedDTO savedAlarmFeed = alarmFeedService.saveAlarmFeed(dto, imageFiles,customUserDetails.getUserId());
        return ResponseEntity.ok(com.team.youarelikemetoo.global.util.ApiResponse.success(savedAlarmFeed));
    }

}
