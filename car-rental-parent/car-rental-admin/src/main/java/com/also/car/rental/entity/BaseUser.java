package com.also.car.rental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Setter
@Getter
@TableName("sys_user")
public class BaseUser {

    @TableId
    private Integer id;

    @NotEmpty(message = "user.name.empty")
    private String username;

    @NotEmpty(message = "user.pwd.empty")
    private String password;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
