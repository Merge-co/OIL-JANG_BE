package com.mergeco.oiljang.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.user.entity.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class ConvertUtil {

    public static Object convertObjectToJsonObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        String convertJsonString;
        Object convertObject;

        try {
            convertJsonString = mapper.writeValueAsString(obj);
            convertObject = parser.parse(convertJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return convertObject;
    }

    public User convertToEntity(JoinDTO joinDTO){
        User user = User.builder()
                .name(joinDTO.getName())
                .profileImageUrl(joinDTO.getProfileImageUrl())
                .build();

        return user;
    }
}
