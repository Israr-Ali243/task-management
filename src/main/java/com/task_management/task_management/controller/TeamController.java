package com.task_management.task_management.controller;

import com.task_management.task_management.dto.TeamDTO;
import com.task_management.task_management.service.TeamService;
import com.task_management.task_management.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/create-team")
    public BaseResponse createTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.createTeam(teamDTO);
    }

    @GetMapping("/find-all-teams")
    public BaseResponse getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/add-team-member/teamId/{teamId}/userId/{userId}")
    public BaseResponse addMemberToTeam(@PathVariable("teamId") Long teamId,@PathVariable("userId") Long userId){
        return teamService.addMemberToTeam(teamId, userId);
    }

}
