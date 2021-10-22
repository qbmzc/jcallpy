package com.congcong.jcallpy.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author cong
 * @since 2021/10/21 16:29
 */
@Data
public class UploadParam {


    // @NotBlank
    // @ApiModelProperty("脚本名称，唯一")
    // private String fileName;
    /**
     * 描述
     *
     */
    @NotBlank
    @ApiModelProperty("脚本描述")
    private String remarks;
}
