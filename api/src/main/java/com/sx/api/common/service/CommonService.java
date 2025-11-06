package com.sx.api.common.service;

import com.sx.api.bwry.entity.po.PgbwGw;
import com.sx.api.bwry.entity.po.PgbwRyxxb;
import com.sx.api.bwry.entity.po.PgbwYgdwb;
import com.sx.api.common.bo.CodeBO;
import com.sx.api.common.bo.SelectBO;
import com.sx.sxflow.common.vo.RetCode;


/**
 * @author jiangshuai
 */
public interface CommonService {
    /**
     * <p color=yellow>根据代码表获取数据list</p>
     */
    RetCode selectCodeList(CodeBO bo);
    /**
     * <p color=yellow>根据登陆人查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     */
    RetCode selectYgdwxxList(String dwbh);
    /**
     * <p color=yellow>根据入参查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     */
    RetCode selectYgdwxxListUsBO(SelectBO bo);

    /**
     * <p color=yellow>判断单位性质</p>
     */
    boolean boolYgdwxx(String flag,String dwbh);

    /**
     * <p color=yellow>获取用工单位信息</p>
     */
    PgbwYgdwb selectYgdwb(String dwbh);

    PgbwGw selectGw(String gwbh);
    /**
     * <p color=yellow>获取人员信息</p>
     */
    PgbwRyxxb selectRyxx (String ryid);

    /**
     * <p color=yellow>获取代码名称</p>
     */
    String getDmmc(String tableName, String id);
}
