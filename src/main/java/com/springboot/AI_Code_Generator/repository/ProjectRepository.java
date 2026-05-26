package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("""
        SELECT p FROM Project p
            where p.deletedAt IS NULL
                    ORDER BY p.updatedAt DESC
        """)
    List<Project> findAllAccessibleByUser(@Param("userid") Long userId);

    // below query can be written without join also, but fetch makes this more efficient
    @Query(
            """
            SELECT p FROM Project p
                        WHERE p.id=:projectid AND p.deletedAt IS NULL
            """
    )
    Optional<Project> findAccessibleProjectById(@Param("projectid") Long projectId, @Param("userid") Long userId);

}
