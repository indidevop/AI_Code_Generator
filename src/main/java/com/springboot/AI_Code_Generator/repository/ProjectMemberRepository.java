package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.ProjectMemberId;
import com.springboot.AI_Code_Generator.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    List<ProjectMember> findByIdProjectId(Long projectId);

    // good practice - fetch only the required data, here we are not fetching the complete object
    @Query("SELECT pm.projectRole FROM ProjectMember pm " +
            "WHERE pm.id.projectId=:projectId " +
            "AND pm.id.userId=:currentUserId")
    Optional<ProjectRole> findRoleByProjectIdAndUserId(@Param("projectId") Long projectId,@Param("currentUserId") Long currentUserId);
}
