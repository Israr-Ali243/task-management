package com.task_management.task_management.service;

import com.task_management.task_management.dto.TeamDTO;
import com.task_management.task_management.utils.BaseResponse;

import java.util.List;

public interface TeamService {
    BaseResponse createTeam(TeamDTO teamDTO);
    BaseResponse getAllTeams();
    BaseResponse addMemberToTeam(Long teamId, Long userId);
}
