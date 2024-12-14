package com.qbp.model.vo;

import com.qbp.model.entity.Resource;
import lombok.Data;

import java.util.List;


@Data
public class LoginVO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Integer gender;
    private String status;
    private List<Resource> resources;
}
