package com.team.youarelikemetoo.alarmFeed.controller;


import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.service.AlarmFeedService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @PostMapping
    public ResponseEntity<?> getSaveAlarmFeedRequest(@RequestPart("data") AlarmFeedDTO dto,
                                                     @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails){
        AlarmFeedDTO savedAlarmFeed = alarmFeedService.saveAlarmFeed(dto, imageFiles,customUserDetails.getUserId());
        return ResponseEntity.ok(com.team.youarelikemetoo.global.util.ApiResponse.success(savedAlarmFeed));
    }

    @Operation(summary = "알람 피드를 조회합니다.", description = "요청한 id의 알람피드를 조회합니다.")
    @GetMapping("/{alarmFeedId}")
    public ResponseEntity<?> getReadAlarmFeedIdRequest(@PathVariable Long alarmFeedId){
        AlarmFeedDTO dto = alarmFeedService.getAlarmFeedDTO(alarmFeedId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @Operation(summary = "알람 피드를 수정합니다.", description = "요청한 id의 알람피드를 수정합니다.")
    @PutMapping("/{alarmFeedId}")
    public ResponseEntity<?> getUpdateAlarmFeedRequest(@RequestPart("data") AlarmFeedDTO dto,
                                                     @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails){
        AlarmFeedDTO savedAlarmFeed = alarmFeedService.updateAlarmFeed(dto, imageFiles, customUserDetails.getUserId());
        return ResponseEntity.ok(com.team.youarelikemetoo.global.util.ApiResponse.success(savedAlarmFeed));
    }

    @Operation(summary = "알람 피드를 삭제합니다.", description = "요청한 알람피드의 id를 삭제합니다.")
    @DeleteMapping("/{alarmFeedId}")
    public ResponseEntity<?> getDeleteAlarmFeedRequest(@PathVariable Long alarmFeedId){
        alarmFeedService.deleteAlarmFeed(alarmFeedId);

        return ResponseEntity.ok(ApiResponse.success(alarmFeedId + " 의 알람피드가 정상적으로 삭제되었습니다."));
    }



}
