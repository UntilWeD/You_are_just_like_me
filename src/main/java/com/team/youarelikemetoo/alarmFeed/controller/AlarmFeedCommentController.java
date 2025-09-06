package com.team.youarelikemetoo.alarmFeed.controller;

import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import com.team.youarelikemetoo.alarmFeed.service.AlarmFeedCommentService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/alarmFeedComment")
public class AlarmFeedCommentController {

    private final AlarmFeedCommentService alarmFeedCommentService;


    @Operation(summary = "알람 피드 코멘트를 저장합니다.", description = "알람 피드 코멘트를 저장합니다.")
    @PostMapping
    public ResponseEntity<?> getSaveAlarmFeedCommentRequest(@RequestBody AlarmFeedCommentDTO dto,
                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails){
        AlarmFeedCommentDTO alarmFeedCommentDTO = alarmFeedCommentService.saveAlarmFeedComment(dto, customUserDetails.getUserId());

        return ResponseEntity.ok(ApiResponse.success(alarmFeedCommentDTO));
    }

    @Operation(summary = "해당 id의 알람피드 코멘트리스트를 조회합니다.", description = "알람 피드 코멘트 리스트를 조회합니다.")
    @GetMapping("/{alarmFeedId}")
    public ResponseEntity<?> getAlarmFeedCommentListRequest(@PathVariable Long alarmFeedId){

        List<AlarmFeedCommentDTO> dto = alarmFeedCommentService.readAlarmFeedCommentList(alarmFeedId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @Operation(summary = "알람 피드 코멘트를 수정합니다.", description = "알람 피드 코멘트를 수정합니다.")
    @PutMapping("/{alarmFeedCommentId}")
    public ResponseEntity<?> getUpdateAlarmFeedComment(@PathVariable Long alarmFeedCommentId,
                                                       @RequestBody AlarmFeedCommentDTO alarmFeedCommentDTO){
        AlarmFeedCommentDTO dto = alarmFeedCommentService.updateAlarmFeedComment(alarmFeedCommentId, alarmFeedCommentDTO);

        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @Operation(summary = "알람 피드 코멘트를 삭제합니다.", description = "알람 피드 코멘트를 삭제합니다.")
    @DeleteMapping("/{alarmFeedCommentId}")
    public ResponseEntity<?> getDeleteAlarmFeedCommentRequest(@PathVariable Long alarmFeedCommentId){
        alarmFeedCommentService.deleteAlarmFeedComment(alarmFeedCommentId);
        return ResponseEntity.ok(ApiResponse.success(alarmFeedCommentId + " 의 알람 피드 코멘트가 정상적으로 삭제되었습니다."));
    }





}
