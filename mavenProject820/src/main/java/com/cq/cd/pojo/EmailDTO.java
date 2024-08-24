package com.cq.cd.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {
 
    private String email;
 
    private String code;
 
    private String subject;
 
    private Map<String, Object> commentMap;
 
    private String template;
 
}