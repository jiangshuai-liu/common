package com.sx.api.common.controller;

import com.sx.api.bwry.entity.po.PgbwYgdwb;
import com.sx.api.common.bo.CodeBO;
import com.sx.api.common.bo.SelectBO;
import com.sx.api.common.service.CommonService;
import com.sx.common.CommonConstant;
import com.sx.sxflow.common.base.controller.BaseController;
import com.sx.sxflow.common.config.Config;
import com.sx.sxflow.common.vo.AccessToken;
import com.sx.sxflow.common.vo.RetCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p color=yellow>公共接口</p>
 * @author jiangshuai
 */
@Slf4j
@RestController
@RequestMapping("/pgbw/common")
@Tag(name = "公共接口")
public class CommonController extends BaseController {
    @Resource
    private CommonService service;
    /**
     * <p color=yellow>根据代码表获取数据list</p>
     * @param bo
     * @return RetCode
     */
    @PostMapping("/getCodeList")
    public RetCode getCodeList(@RequestBody @Validated CodeBO bo) {
        return service.selectCodeList(bo);
    }

    /**
     * <p color=yellow>根据登陆人查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     * @param request
     * @return RetCode
     */
    @GetMapping("/getDwxxList")
    public RetCode getDwxxList(HttpServletRequest request) {
        String tokenkey = request.getHeader(Config.tokenKeyCookie);
        if (ObjectUtils.isEmpty(tokenkey)){
            tokenkey = this.getAccessTokenKey(request, Config.tokenKeyCookie);
        }
        AccessToken accessToken = this.getAccessToken(request, Config.tokenKeyPrefix, tokenkey);
        String dwbh="";
        if(!service.boolYgdwxx(CommonConstant.DWXZ_BGS,accessToken.getUserDId())){
            dwbh=accessToken.getUserDId();
        }
        return service.selectYgdwxxList(dwbh);
    }
    /**
     * <p color=yellow>根据入参查询对应单位List</p>
     * 用工单位下拉框使用 根据不同登录人返回不同单位集合
     * @param request
     * @return RetCode
     */
    @PostMapping("/getDwxxListUsBO")
    public RetCode getDwxxListUsBO(HttpServletRequest request, @RequestBody(required = false) SelectBO bo) {
        String tokenkey = request.getHeader(Config.tokenKeyCookie);
        if (ObjectUtils.isEmpty(tokenkey)){
            tokenkey = this.getAccessTokenKey(request, Config.tokenKeyCookie);
        }
        AccessToken accessToken = this.getAccessToken(request, Config.tokenKeyPrefix, tokenkey);
        if(!service.boolYgdwxx(CommonConstant.DWXZ_BGS,accessToken.getUserDId())){
            bo.setDwbh(accessToken.getUserDId());
        }else{
            bo.setDwbh("");
        }
        return service.selectYgdwxxListUsBO(bo);
    }
    /**
     * <p color=yellow>判断单位信息是否为办公室</p>
     * @param request
     * @return RetCode
     */
    @GetMapping("/getDlDwxxIsBgs")
    public RetCode getDlDwxxIsBgs(HttpServletRequest request) {
        String tokenkey = request.getHeader(Config.tokenKeyCookie);
        if (ObjectUtils.isEmpty(tokenkey)){
            tokenkey = this.getAccessTokenKey(request, Config.tokenKeyCookie);
        }
        AccessToken accessToken = this.getAccessToken(request, Config.tokenKeyPrefix, tokenkey);
        if(service.boolYgdwxx(CommonConstant.DWXZ_BGS,accessToken.getUserDId())){
            //办公室登陆
              return RetCode.buildSuccess(false);
        }
        return RetCode.buildSuccess(true);
    }
    /**
     * <p color=yellow>获取登陆单位信息</p>
     * @param request
     * @return RetCode
     */
    @GetMapping("/getDlDwxx")
    public RetCode getDlDwxx(HttpServletRequest request) {
        String tokenkey = request.getHeader(Config.tokenKeyCookie);
        if (ObjectUtils.isEmpty(tokenkey)){
            tokenkey = this.getAccessTokenKey(request, Config.tokenKeyCookie);
        }
        AccessToken accessToken = this.getAccessToken(request, Config.tokenKeyPrefix, tokenkey);
        PgbwYgdwb pgbwYgdwb=service.selectYgdwb(accessToken.getUserDId());
        return RetCode.buildSuccess(pgbwYgdwb);
    }
}
