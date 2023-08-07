package com.wly.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.competition.model.domain.Competition;
import com.wly.competition.model.dto.CompetitionQuery;

import java.util.List;

/**
 *
 */
public interface CompetitionService extends IService<Competition> {

    List<Competition> listCompetitions(CompetitionQuery competitionQuery, boolean isAdmin);
}
