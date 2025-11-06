package com.sx.api.common.bo;

import com.sx.common.string.StringUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangshuai
 * @Description 获取代码表
 * @date 2024/12/18
 * @time 15:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeBO {

    /**
     * 表名
     */
    @NotBlank(message = "表名不可为空！")
    private String tableName;

    /**
     * 获取表名
     */
    public String getTableName(){
        if(StringUtils.isTableName(this.tableName)){
            return this.tableName;
        }else {
            return null;
        }
    }
}
