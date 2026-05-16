package com.springboot.AI_Code_Generator.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    // one project can have only one owner
    // one owner can have many projects
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false) // foreign key column, nullable is false means project must have an owner defined
    User owner;

    // this will be ignored by builder if below annotation is not used
    // Builder expects every field to be handled explicitly
    @Builder.Default
    Boolean isPublic = Boolean.FALSE;

    @CreationTimestamp
    Instant createdAt;
    @UpdateTimestamp
    Instant updatedAt;
    Instant deletedAt; //soft delete

}
