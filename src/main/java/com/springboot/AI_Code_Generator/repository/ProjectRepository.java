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
                    AND EXISTS(
                            SELECT 1 FROM ProjectMember pm
                                    WHERE pm.id.userId = :userId
                                            AND pm.id.projectId = p.id
                            )
                    ORDER BY p.updatedAt DESC
        """)
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    // below query can be written without join also, but fetch makes this more efficient
    @Query(
            """
            SELECT p FROM Project p
                        WHERE p.id=:projectId AND p.deletedAt IS NULL
                                    AND EXISTS(
                            SELECT 1 FROM ProjectMember pm
                                    WHERE pm.id.userId = :userId
                                            AND pm.id.projectId = :projectId
                            )
            """
    )
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId, @Param("userId") Long userId);

}
