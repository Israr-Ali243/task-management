package com.task_management.task_management.utils;

import com.task_management.task_management.dto.CommentDTO;
import com.task_management.task_management.dto.TaskDTO;
import com.task_management.task_management.dto.TeamDTO;
import com.task_management.task_management.dto.UserDTO;
import com.task_management.task_management.model.Comment;
import com.task_management.task_management.model.Task;
import com.task_management.task_management.model.Team;
import com.task_management.task_management.model.User;

import java.util.stream.Collectors;

public class Mapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static User toUserEntity(UserDTO req) {
        if (req == null) return null;
        return User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .role(req.getRole())
                .build();
    }

    public static TeamDTO toTeamDTO(Team team) {
        if (team == null) return null;
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .memberIds(team.getMembers() != null ?
                        team.getMembers().stream().map(User::getId).collect(Collectors.toSet()) : null)
                .build();
    }

    public static Team toTeamEntity(TeamDTO req) {
        if (req == null) return null;
        return Team.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
    }
    public static TaskDTO toTaskDTO(Task task) {
        if (task == null) return null;
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)
                .teamId(task.getTeam() != null ? task.getTeam().getId() : null)
                .build();
    }

    public static Task toTaskEntity(TaskDTO req, User assignedTo, Team team) {
        if (req == null) return null;
        return Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .priority(req.getPriority())
                .status(Status.TODO)
                .dueDate(req.getDueDate())
                .assignedTo(assignedTo)
                .team(team)
                .build();
    }

    public static void updateTaskEntity(Task task, TaskDTO req, User assignedTo, Team team) {
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setDueDate(req.getDueDate());
        if (assignedTo != null) task.setAssignedTo(assignedTo);
        if (team != null) task.setTeam(team);
    }


    public static CommentDTO toCommentDTO(Comment comment) {
        if (comment == null) return null;
        return CommentDTO.builder()
                .id(comment.getId())
                .taskId(comment.getTask() != null ? comment.getTask().getId() : null)
                .commentText(comment.getCommentText())
                .createdById(comment.getCreatedBy() != null ? comment.getCreatedBy().getId() : null)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment toCommentEntity(CommentDTO dto) {
        if (dto == null) return null;

        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setCommentText(dto.getCommentText());
        comment.setCreatedAt(dto.getCreatedAt());

        if (dto.getTaskId() != null) {
            Task task = new Task();
            task.setId(dto.getTaskId());
            comment.setTask(task);
        }

        if (dto.getCreatedById() != null) {
            User user = new User();
            user.setId(dto.getCreatedById());
            comment.setCreatedBy(user);
        }

        return comment;
    }


}
