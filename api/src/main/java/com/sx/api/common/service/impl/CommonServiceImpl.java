package com.sx.api.common.service.impl;

import com.sx.api.bwry.entity.po.PgbwGw;
import com.sx.api.bwry.entity.po.PgbwRyxxb;
import com.sx.api.bwry.entity.po.PgbwYgdwb;
import com.sx.api.common.bo.CodeBO;
import com.sx.api.common.bo.SelectBO;
import com.sx.api.common.mapper.CommonMapper;
import com.sx.api.common.service.CommonService;
import com.sx.common.helper.StringHelper;
import com.sx.common.string.StringUtils;
import com.sx.sxflow.common.vo.RetCode;
import com.sx.sxflow.config.SxflowExtensionMsgEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author jiangshuai
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonMapper commonMapper;
    /**
     * <p color=yellow>根据代码表获取数据list</p>
     */
    @Override
    public RetCode selectCodeList(CodeBO req) {
        if(StringHelper.isEmpty(StringUtils.isTableName(req.getTableName()))){
            return RetCode.buildError(SxflowExtensionMsgEnum.SYSTEM_ERROR.getMsg());
        }
        return RetCode.buildSuccess(commonMapper.getCodeList(req.getTableName()));
    }

    /**
     * <p color=yellow>根据登陆人查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     */
    @Override
    public RetCode selectYgdwxxList(String dwbh) {
        return RetCode.buildSuccess(commonMapper.getYgdwxxList(dwbh));
    }
    /**
     * <p color=yellow>根据登陆人查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     */
    @Override
    public RetCode selectYgdwxxListUsBO(SelectBO bo) {
        return RetCode.buildSuccess(commonMapper.getYgdwxxListUsBO(bo));
    }
    /**
     *  <p color=yellow>根据单位编号查询单位信息</p>
     */
    @Override
    public boolean boolYgdwxx(String flag,String dwbh) {
        boolean bool=false;
        PgbwYgdwb pgbwYgdwb=commonMapper.getYgdwxx(dwbh);
        if(StringHelper.isNotEmpty(pgbwYgdwb)){
            bool=flag.equals(pgbwYgdwb.getDwsjxz());
        }
        return bool;
    }
    /**
     * <p color=yellow>获取用工单位信息</p>
     */
    @Override
    public PgbwYgdwb selectYgdwb(String dwbh) {
        return commonMapper.getYgdwxx(dwbh);
    }

    /**
     * <p color=yellow>获取岗位信息</p>
     */
    @Override
    public PgbwGw selectGw(String gwbh) {
        return commonMapper.getGw(gwbh);
    }

    /**
     * <p color=yellow>获取人员信息</p>
     */
    @Override
    public PgbwRyxxb selectRyxx(String ryid) {
        return commonMapper.getryxx(ryid);
    }

    /**
     * <p color=yellow>获取代码对应代码</p>
     */
    @Override
    public String getDmmc(String tableName, String id) {
        if(StringHelper.isNotEmpty(StringUtils.isTableName(tableName))){
            return commonMapper.getDmmc(tableName,id);
        }else {
            return "";
        }
    }

}
