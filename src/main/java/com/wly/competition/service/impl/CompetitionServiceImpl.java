package com.wly.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.competition.common.ErrorCode;
import com.wly.competition.exception.BusinessException;
import com.wly.competition.mapper.CompetitionMapper;
import com.wly.competition.model.domain.*;
import com.wly.competition.model.dto.CompetitionQuery;
import com.wly.competition.model.request.CompetitionJoinRequest;
import com.wly.competition.model.vo.TeamCompetitionVO;
import com.wly.competition.service.CompetitionService;
import com.wly.competition.service.TeamCompetitionService;
import com.wly.competition.service.TeamService;
import com.wly.competition.service.UserTeamService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wly.competition.constant.UserConstant.USER_LOGIN_STATE;

/**
 *
 */
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition>
    implements CompetitionService {

    @Resource
    private TeamService teamService;

    @Resource
    private TeamCompetitionService teamCompetitionService;

    @Resource
    private UserTeamService userTeamService;

    @Override
    public List<Competition> listCompetitions(CompetitionQuery competitionQuery, boolean isAdmin) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        //组合查询条件
        if(competitionQuery != null){
            Long id = competitionQuery.getId();
            if(id != null && id > 0){
                queryWrapper.eq("id", id);
            }
            String name = competitionQuery.getName();
            if(StringUtils.isBlank(name)){
                queryWrapper.like("name", name);
            }
            String level = competitionQuery.getLevel();
            if(StringUtils.isBlank(level)){
                queryWrapper.like("level", level);
            }
        }
        //不展示已过期的竞赛
        queryWrapper.and(qw -> qw.ge("expireTime", new Date()).or().isNull("expireTime"));
        List<Competition> competitionList = this.list();
        if(CollectionUtils.isEmpty(competitionList)){
            return new ArrayList<>();
        }

        return competitionList;
    }

    @Override
    public List<TeamCompetitionVO> listCompetitions1(CompetitionQuery competitionQuery, HttpServletRequest request) {

        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        //组合查询条件
        if(competitionQuery != null){
            Long id = competitionQuery.getId();
            if(id != null && id > 0){
                queryWrapper.eq("id", id);
            }
            String name = competitionQuery.getName();
            if(StringUtils.isBlank(name)){
                queryWrapper.like("name", name);
            }
            String level = competitionQuery.getLevel();
            if(StringUtils.isBlank(level)){
                queryWrapper.like("level", level);
            }
        }
        //不展示已过期的竞赛
        queryWrapper.and(qw -> qw.ge("expireTime", new Date()).or().isNull("expireTime"));
        List<Competition> competitionList = this.list();
        if(CollectionUtils.isEmpty(competitionList)){
            return new ArrayList<>();
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        Long userId = user.getId();
        List<TeamCompetitionVO> teamCompetitionVOList = new ArrayList<>();
        List<TeamCompetition> teamCompetitionList = teamCompetitionService.list();
        for(Competition competition : competitionList){
            for(TeamCompetition teamCompetition : teamCompetitionList){
                if(competition.getId().equals(teamCompetition.getCompetitionId())){
                    //以队长的身份参加该竞赛
                    if( userId.equals(teamCompetition.getUserId())) {
                        TeamCompetitionVO teamCompetitionVO = new TeamCompetitionVO();
                        //BeanUtils.copyProperties(competition, teamCompetitionVO);
                        teamCompetitionVO.setId(teamCompetition.getId());
                        teamCompetitionVO.setCompetitionId(teamCompetition.getCompetitionId());
                        teamCompetitionVO.setName(competition.getName());
                        teamCompetitionVO.setLevel(competition.getLevel());
                        teamCompetitionVO.setCreateTime(competition.getCreateTime());
                        teamCompetitionVO.setExpireTime(competition.getExpireTime());
                        //所在队伍是否参加比赛
                        teamCompetitionVO.setHasJoin(true);
                        //是否是所在队伍的队长
                        teamCompetitionVO.setLeader(true);
                        teamCompetitionVOList.add(teamCompetitionVO);
                        break;
                    }else{
                        //以队员的身份参加该竞赛
                        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
                        userTeamQueryWrapper.eq("teamId", teamCompetition.getTeamId());
                        List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
                        for(UserTeam userTeam : userTeamList){
                            if(userId.equals(userTeam.getUserId())){
                                TeamCompetitionVO teamCompetitionVO = new TeamCompetitionVO();
                                //BeanUtils.copyProperties(competition, teamCompetitionVO);
                                teamCompetitionVO.setId(teamCompetition.getId());
                                teamCompetitionVO.setCompetitionId(teamCompetition.getCompetitionId());
                                teamCompetitionVO.setName(competition.getName());
                                teamCompetitionVO.setLevel(competition.getLevel());
                                teamCompetitionVO.setCreateTime(competition.getCreateTime());
                                teamCompetitionVO.setExpireTime(competition.getExpireTime());
                                //所在队伍是否参加比赛
                                teamCompetitionVO.setHasJoin(true);
                                //是否是所在队伍的队长
                                teamCompetitionVO.setLeader(false);
                                teamCompetitionVOList.add(teamCompetitionVO);
                                break;
                            }
                        }
                    }
                }
            }

        }
        return teamCompetitionVOList;
    }

    @Override
    public boolean joinCompetition(CompetitionJoinRequest competitionJoinRequest, User loginUser) {
        if(competitionJoinRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取想要参加的竞赛的id
        Long competitionId = competitionJoinRequest.getCompetitionId();
        if(competitionId == null || competitionId <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取代表的队伍的名称
        String teamName = competitionJoinRequest.getTeamName();
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(teamName)){
            teamQueryWrapper.eq("name", teamName);
        }
        //获取当前用户所想要代表的队伍
        Team team = teamService.getOne(teamQueryWrapper);
        if(team == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "您所提供的队伍名称有误");
        }
        Date expireTime = team.getExpireTime();
        if (expireTime != null && expireTime.before(new Date())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍已过期");
        }
        //不能重复加入竞赛
        QueryWrapper<TeamCompetition> teamCompetitionQueryWrapper = new QueryWrapper<>();
        teamCompetitionQueryWrapper.eq("id", competitionId);
        teamCompetitionQueryWrapper.eq("teamId", team.getId());
        long hasJoinCount = teamCompetitionService.count(teamCompetitionQueryWrapper);
        if(hasJoinCount > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "您所在队伍已经参加该竞赛");
        }
        TeamCompetition teamCompetition = new TeamCompetition();
        teamCompetition.setCompetitionId(competitionId);
        teamCompetition.setTeamId(team.getId());
        teamCompetition.setUserId(loginUser.getId());
        return teamCompetitionService.save(teamCompetition);
    }
}




