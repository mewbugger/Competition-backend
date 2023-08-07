package com.wly.competition.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 无解亦有解
 * @Date: 2023/08/07/16:28
 * @Description:
 */
@TableName(value ="competition")
@Data
public class Competition {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛等级
     */
    private String level;

    /**
     * 过期时间
     */
    private Date expireTime;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Byte isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
