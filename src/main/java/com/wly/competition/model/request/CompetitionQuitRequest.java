package com.wly.competition.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 无解亦有解
 * @Date: 2023/08/20/16:43
 * @Description:
 */
@Data
public class CompetitionQuitRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long competitionId;

    private String teamName;
}
