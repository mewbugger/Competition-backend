package com.wly.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.competition.model.domain.Competition;
import com.wly.competition.model.domain.TeamCompetition;
import com.wly.competition.model.domain.User;
import com.wly.competition.model.dto.CompetitionQuery;
import com.wly.competition.model.request.CompetitionJoinRequest;
import com.wly.competition.model.vo.TeamCompetitionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
public interface CompetitionService extends IService<Competition> {

    boolean joinCompetition(CompetitionJoinRequest competitionJoinRequest, User loginUser);

    List<Competition> listCompetitions(CompetitionQuery competitionQuery, boolean isAdmin);

    List<TeamCompetitionVO> listCompetitions1(CompetitionQuery competitionQuery, HttpServletRequest request);
}
