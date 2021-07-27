package com.hansoleee.exmultipledb.team.controller;

import com.hansoleee.exmultipledb.team.domain.Team;
import com.hansoleee.exmultipledb.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    @PostMapping("/teams")
    public Long save(Team team) {
        Team saveTeam = teamRepository.save(team);
        return saveTeam.getId();
    }
}
