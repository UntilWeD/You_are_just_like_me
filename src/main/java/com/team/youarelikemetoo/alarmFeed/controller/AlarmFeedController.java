package com.team.youarelikemetoo.alarmFeed.controller;


import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.dto.DayOfWeekRequest;
import com.team.youarelikemetoo.alarmFeed.service.AlarmFeedService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/alarmFeed")
@RequiredArgsConstructor
@Slf4j
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

    @Operation(summary = "요일에 따라 해당하는 알람피드들을 반환합니다.",
            description = "해당하는 요일 리스트가 오면 이와 같은 요일리스트를 가진 행들을 조회하여 10개의 항목이 있는 리스트 형태로 반환합니다.")
    @GetMapping ("/by-day")
    public ResponseEntity<?> getAlarmFeedsByDayOfWeekRequest(@RequestParam(required = false) List<Integer> days){
        List<AlarmFeedDTO> alarmFeedDTOS = alarmFeedService.getAlarmFeedsByDayOfWeek(days);

        return ResponseEntity.ok(ApiResponse.success(alarmFeedDTOS));
    }

    @Operation(summary = "요일에 따라 해당하는 내 알람피드들을 반환합니다.",
            description = "해당하는 요일 리스트와 jwt토큰으로부터 userId를 추출하여 그 값들을 기반으로 알람피드들을 조회하여 반환합니다.")
    @GetMapping("/by-day-userId")
    public ResponseEntity<?> getMyAlarmFeedByDayOfWeekAndUserIdRequest(@RequestParam(required = false)  List<Integer> days,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<AlarmFeedDTO> alarmFeedDTOS = alarmFeedService.getAlarmFeedsByDayOfWeekAndUserId(days, customUserDetails.getUserId());


        return ResponseEntity.ok(ApiResponse.success(alarmFeedDTOS));
    }


    // ------ 좋아요 ------
    @Operation(summary = "해당 Id의 알람 피드에 좋아요 횟수를 증가시킵니다.", description = "요청한 id의 알람피드에 대한 좋아요 기록을 저장합니다.")
    @GetMapping("/{alarmFeedId}/like")
    public ResponseEntity<?> getLikeAlarmFeedRequest(@PathVariable Long alarmFeedId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        boolean result = alarmFeedService.addAlarmFeedLike(alarmFeedId, customUserDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(result));
    }


    // ------ 공유 ------
    @Operation(summary = "해당 Id의 알람 피드를 공유합니다.", description = "요청한 id의 알람피드를 공유하고 공유 기록을 저장합니다. ")
    @GetMapping("/{alarmFeedId}/share")
    public ResponseEntity<?> getShareAlarmFeedRequest(@PathVariable Long alarmFeedId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        boolean result = alarmFeedService.shareAlarmFeed(alarmFeedId, customUserDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(result));
    }


}
