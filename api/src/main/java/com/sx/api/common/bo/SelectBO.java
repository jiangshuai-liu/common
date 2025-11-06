package com.sx.api.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title SelectBO
 * @Author jiangshuai
 * @Date 2025/2/25 13:50
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectBO {
    @ApiModelProperty("单位性质")
    private String dwxz;
    @ApiModelProperty("单位编号")
    private String dwbh;
}
