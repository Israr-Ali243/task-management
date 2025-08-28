package com.task_management.task_management.service.serviceImpl;

import com.task_management.task_management.dto.TeamDTO;
import com.task_management.task_management.model.Team;
import com.task_management.task_management.model.User;
import com.task_management.task_management.repository.TeamRepository;
import com.task_management.task_management.repository.UserRepository;
import com.task_management.task_management.service.TeamService;
import com.task_management.task_management.utils.BaseResponse;
import com.task_management.task_management.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public BaseResponse<TeamDTO> createTeam(TeamDTO teamDTO) {
        log.info("Attempting to create a new team: {}", teamDTO.getName());
        try {
            if (teamRepository.existsByName(teamDTO.getName())) {
                log.warn("Team with name '{}' already exists", teamDTO.getName());
                return BaseResponse.failure(
                        "Team with the same name already exists.",
                        true,
                        HttpStatus.CONFLICT.value()
                );
            }

            Team createdTeam = Mapper.toTeamEntity(teamDTO);

            Set<User> members = teamDTO.getMemberIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new DuplicateKeyException("User not found with id: " + id)))
                    .collect(Collectors.toSet());

            createdTeam.setMembers(members);

            Team savedTeam = teamRepository.save(createdTeam);

            log.info("Team created successfully with ID: {}", savedTeam.getId());
            return BaseResponse.success(
                    Mapper.toTeamDTO(savedTeam),
                    "Team has been created successfully", HttpStatus.CREATED.value()
            );

        } catch (DuplicateKeyException e){
            log.error("Duplicate key found while creating team: {}", teamDTO, e);
            return BaseResponse.failure("Duplicate key found while creating the team.",true, HttpStatus.BAD_REQUEST.value());
        }
        catch (DataIntegrityViolationException ex) {
            log.error("Data integrity violation while creating team: {}", teamDTO, ex);
            return BaseResponse.failure("Team creation failed due to duplicate or invalid data.",true, HttpStatus.BAD_REQUEST.value());

        } catch (Exception ex) {
            log.error("Unexpected error occurred while creating team: {}", teamDTO, ex);
            return BaseResponse.failure("An unexpected error occurred while creating the team.",true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
    @Override
    public BaseResponse getAllTeams() {
        try {
            log.info("Fetching all teams...");
            List<TeamDTO> teams = teamRepository.findAll()
                    .stream()
                    .map(Mapper::toTeamDTO)
                    .collect(Collectors.toList());

            log.info("Fetched {} teams successfully", teams.size());
            return BaseResponse.success(teams, "Teams fetched successfully", HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error while fetching teams", e);
            return BaseResponse.failure(null, "Failed to fetch teams: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }

        }
        @Override
        public BaseResponse addMemberToTeam (Long teamId, Long userId){
            try {
                log.info("Adding user with ID {} to team with ID {}", userId, teamId);

                Team team = teamRepository.findById(teamId)
                        .orElseThrow(() -> {
                            log.warn("Team with ID {} not found", teamId);
                            return new IllegalArgumentException("Team not found with ID: " + teamId);
                        });

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> {
                            log.warn("User with ID {} not found", userId);
                            return new IllegalArgumentException("User not found with ID: " + userId);
                        });

                if (team.getMembers().contains(user)) {
                    return BaseResponse.failure("Member with ID " + userId + " already exists", true, HttpStatus.CONFLICT.value());
                }
                team.getMembers().add(user);
                Team updatedTeam = teamRepository.save(team);

                log.info("User {} successfully added to Team {}", userId, teamId);

                return BaseResponse.success(
                        Mapper.toTeamDTO(updatedTeam),
                        "User added to team successfully", HttpStatus.OK.value()
                );
            } catch (IllegalArgumentException e) {
                log.error("Validation error while adding user {} to team {}: {}", userId, teamId, e.getMessage());
                return BaseResponse.failure(null, "Validation failed: " + e.getMessage(), HttpStatus.FORBIDDEN.value());
            } catch (Exception e) {
                log.error("Unexpected error while adding user {} to team {}", userId, teamId, e);
                return BaseResponse.failure(null, "Failed to add member to team: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            }
        }


}
