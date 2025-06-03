package com.team.youarelikemetoo.alarm.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Converter
public class DayOfWeekConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        if (list == null || list.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++){
            sb.append(list.get(i));
            if(i < list.size() - 1)
                sb.append(",");
        }

        return sb.toString();
    }

    @Override
    public List<Integer> convertToEntityAttribute(String str) {
        List<Integer> result = new ArrayList<>();

        if(str == null || str.trim().isEmpty()){
            return result;
        }

        String[] parts = str.split(",");
        for(String part : parts){
            try{
                result.add(Integer.parseInt(part));
            } catch (NumberFormatException exception){
                log.info("[DayOfWeekConverter] DayOfWeek 문자열을 리스트로 변환하던중 " + exception + "예외 발생");
            }
        }


        return result;
    }
}
