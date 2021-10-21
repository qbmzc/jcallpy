package com.congcong.jcallpy.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cong
 * @since 2021/10/21 16:29
 */
@Data
public class UploadParam {


    @ApiModelProperty("脚本名称，唯一")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty("脚本描述")
    private String describe;
}
