package com.wly.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.competition.mapper.CompetitionMapper;
import com.wly.competition.model.domain.Competition;
import com.wly.competition.model.dto.CompetitionQuery;
import com.wly.competition.service.CompetitionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition>
    implements CompetitionService {

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
}




