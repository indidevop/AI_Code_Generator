package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.member.MemberResponse;
import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target="userId",source="id")
    @Mapping(target="projectRole",constant="OWNER")
    MemberResponse userToMemberResponseFromOwner(User user);

    MemberResponse projectMemberToMemberResponse(ProjectMember projectMember);
}
