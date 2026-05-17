package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("""
        select p from Project p
        where p.owner.id=:userid AND
        p.deletedAt IS NULL
        ORDER BY p.updatedAt DESC
        """)
    List<Project> findAllAccessibleByUser(@Param("userid") Long userId);

}
