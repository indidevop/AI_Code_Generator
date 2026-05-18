package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
}
