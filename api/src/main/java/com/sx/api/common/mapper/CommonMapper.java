package com.sx.api.common.mapper;

import com.sx.api.bwry.entity.po.PgbwGw;
import com.sx.api.bwry.entity.po.PgbwRyxxb;
import com.sx.api.bwry.entity.po.PgbwYgdwb;
import com.sx.api.common.bo.SelectBO;
import com.sx.api.common.vo.CodeVO;
import com.sx.api.common.vo.YgdwxxVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 编外人员公共mapper
 * </p>
 *
 * @author jiangshuai
 * @since 20241218
 */
@Mapper
public interface CommonMapper {

    /**
     * <p color=yellow>根据表名查询对应代码表数据</p>
     * 下拉框使用
     * @param tableName 表名
     * @return List<CodeVO>
     */
    List<CodeVO> getCodeList(@Param("name") String tableName);
    /**
     * <p color=yellow>根据单位编号查询单位名称</p>
     * @param dwbh 单位编号
     * @return String
     */
    String getDwxx(@Param("id") String dwbh);

    /**
     * <p color=yellow>根据岗位编号获取上级目录名称</p>
     * @param gwbh 岗位编号
     * @return String
     */
    String getDlGwxx(@Param("id") String gwbh);


    /**
     * <p color=yellow>获取用工单位信息List</p>
     * 根据 单位编号 查询用工单位信息 为空时查询所有
     * @param dwbh 单位编号
     * @return List<YgdwxxVO>
     */
    List<YgdwxxVO> getYgdwxxList(@Param("id") String dwbh);

    /**
     * <p color=yellow>获取用工单位信息List</p>
     * 根据 单位编号 查询用工单位信息
     * @param bo
     * @return List<YgdwxxVO>
     */
    List<YgdwxxVO> getYgdwxxListUsBO(@Param("bo") SelectBO bo);

    /**
     * <p color=yellow>获取用工单位信息</p>
     * @param dwbh 单位编号
     * @return PgbwYgdwb
     */
    PgbwYgdwb getYgdwxx(@Param("id") String dwbh);

    /**
     * <p color=yellow>获取岗位信息</p>
     * @param gwbh
     * @return PgbwGw
     */
    PgbwGw getGw(@Param("id") String gwbh);
    /**
     * <p color=yellow>获取人员信息</p>
     * @param ryid
     * @return PgbwGw
     */
    PgbwRyxxb getryxx(@Param("id") String ryid);

    /**
     * <p color=yellow>获取字典表名称</p>
     * @param tableName 表名
     * @param id 字典表id
     * @return String
     */
    String getDmmc(String tableName, String id);
}
