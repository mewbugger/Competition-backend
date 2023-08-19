package com.wly.competition.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 无解亦有解
 * @Date: 2023/08/19/16:22
 * @Description:    队伍竞赛脱敏信息
 */
@Data
public class TeamCompetitionVO implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 竞赛id
     */
    private Long competitionId;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛等级
     */
    private String level;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 过期时间
     */
    private Date expireTime;


    /**
     * 是否已加入队伍
     */
    private boolean hasJoin = false;

    /**
     * 是否以队长身份参与竞赛
     */
    private boolean isLeader = false;
}
