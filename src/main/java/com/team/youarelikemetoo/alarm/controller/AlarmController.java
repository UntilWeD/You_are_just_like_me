package com.team.youarelikemetoo.alarm.controller;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "알람정보를 저장합니다.", description = "요청한 알람을 DB에 저장합니다.")
    @ApiResponse(
            responseCode = "200", description = "알람 저장",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @PostMapping
    public ResponseEntity<?> getSaveAlarmRequest(@RequestBody AlarmDTO alarmDTO){
        return alarmService.saveAlarm(alarmDTO);
    }

    @Operation(summary = "특정 알람 정보를 조회합니다.", description = "요청한 알람의 정보를 조회합니다.")
    @ApiResponse(
            responseCode = "200", description = "알람 조회 성공",
            content = @Content(schema = @Schema(implementation = AlarmDTO.class))
    )
    @GetMapping("/{alarmId}")
    public ResponseEntity<?> getReadAlarmRequest(@PathVariable Long alarmId){
        return alarmService.getAlarm(alarmId);
    }

    @Operation(summary = "알람 정보를 수정합니다.", description = "요청한 알람의 정보를 수정합니다.")
    @ApiResponse(
            responseCode = "200", description = "알람 수정 성공",
            content = @Content(schema = @Schema(implementation = AlarmDTO.class))
    )
    @PutMapping("/{alarmId}")
    public ResponseEntity<?> getUpdateAlarmRequest(@PathVariable Long alarmId,@RequestBody AlarmDTO alarmDTO){
        return alarmService.updateAlarm(alarmId, alarmDTO);
    }

    @Operation(summary = "알람 정보를 삭제합니다.", description = "요청한 알람의 정보를 삭제합니다.")
    @ApiResponse(
            responseCode = "200", description = "알람 삭제 성공",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @DeleteMapping ("/{alarmId}")
    public ResponseEntity<?> getDeleteAlarmRequest(@PathVariable Long alarmId){
        return alarmService.deleteAlarm(alarmId);
    }









}
