package com.hansoleee.exmultipledb.team.repository;

import com.hansoleee.exmultipledb.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
