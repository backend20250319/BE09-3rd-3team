package io.studyit.userservice.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeResultResponse {

    private String before;
    private String after;


}
