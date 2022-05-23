package com.example.SoftwareProject.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {  //Body부분
    //Http응답으로 사용할 DTO
    private String error;
    private List<T> data;
}
