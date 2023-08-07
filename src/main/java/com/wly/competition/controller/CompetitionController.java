package com.wly.competition.controller;
import com.wly.competition.common.BaseResponse;
import com.wly.competition.common.ErrorCode;
import com.wly.competition.common.ResultUtils;
import com.wly.competition.exception.BusinessException;
import com.wly.competition.model.domain.Competition;
import com.wly.competition.model.dto.CompetitionQuery;
import com.wly.competition.service.CompetitionService;
import com.wly.competition.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 无解亦有解
 * @Date: 2023/08/07/16:41
 * @Description:
 */
@RestController
@RequestMapping("/competition")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
public class CompetitionController {

    @Resource
    private CompetitionService competitionService;

    @Resource
    private UserService userService;


    @GetMapping("/list")
    public BaseResponse<List<Competition>> listCompetitions(CompetitionQuery competitionQuery, HttpServletRequest request){
        if(competitionQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断当前用户是否为管理员
        boolean isAdmin = userService.isAdmin(request);
        //查询竞赛列表
        List<Competition> competitionList = competitionService.listCompetitions(competitionQuery, isAdmin);


        return ResultUtils.success(competitionList);
    }



}
