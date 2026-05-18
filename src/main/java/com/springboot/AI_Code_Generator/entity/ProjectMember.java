package com.springboot.AI_Code_Generator.entity;

import com.springboot.AI_Code_Generator.enums.ProjectRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project_member")
public class ProjectMember {

    @EmbeddedId
    ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId")
    Project project;

    @ManyToOne
    @MapsId("userId")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProjectRole projectRole;

    Instant invitedAt;
    Instant acceptedAt;
}

/*

Each ProjectMember row refers to one Project
Each ProjectMember row refers to one User

But the reverse is true too:
One Project can have many ProjectMember entries
One User can be a member of many ProjectMember entries
So from the ProjectMember side, the relationship is many-to-one.

*/

/*
Why MapId is used?

As the primary key already has project_id and user_id columns,
if MapId is not used for foreignKey JPA will again create similar columns,
this will cause issue

 */

/*
Why @ManyToMany is not used
Because the relationship is not just a plain many-to-many link.

ProjectMember is a join entity with extra data:

ProjectRole projectRole
Instant invitedAt
Instant acceptedAt
That means:

it’s not just connecting User and Project
it stores membership metadata too
In JPA, @ManyToMany is only appropriate for a pure join table with no extra columns. Since this app needs additional fields on the membership, it must use an explicit entity (ProjectMember) with two @ManyToOne links instead.

 */