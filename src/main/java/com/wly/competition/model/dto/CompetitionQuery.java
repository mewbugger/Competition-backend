package com.wly.competition.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wly.competition.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 无解亦有解
 * @Date: 2023/08/07/17:12
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompetitionQuery extends PageRequest{
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 搜索关键词（同时对竞赛名称和描述搜索）
     */
    private String searchText;



    /**
     * 竞赛名称
     */
    private String name;


    /**
     * 竞赛等级
     */
    private String level;
}
