package com.congcong.jcallpy.module.python.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cong
 * @since 2021/10/21 14:33
 */
@Entity
@ToString
@Data
public class PythonFile implements Serializable {

    @ApiModelProperty(value = "id,唯一标识")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "路径，执行脚本时需要该参数")
    private String filePath;

    @ApiModelProperty(value = "脚本名称")
    private String fileName;
    @ApiModelProperty(value = "描述说明")
    private String remarks;

    /**
     * 1,启用
     * 0，禁用
     */
    @ApiModelProperty(value = "脚本状态，1，启用，0 禁用")
    private Integer state;

    @ApiModelProperty(value = "创建时间")

    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "版本")
    private Long version;
    @ApiModelProperty(value = "删除标记，1删除，0未删除")
    private Integer isDeleted;

}
