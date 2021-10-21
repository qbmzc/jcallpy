package com.congcong.jcallpy.module.user.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cong
 * @since 2021/10/21 14:23
 */
@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Long version;

    private Integer isDeleted;

}
