package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.exception.ValidationException;
import com.cg.service.GroupMemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/groups")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    
    @PostMapping("/{groupId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addMember(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        
        dto.setGroupId(groupId);

        GroupMemberDto saved = groupMemberService.addMember(dto);

        return new SuccessMessageDto(
                "Member added successfully: userId ",
                saved.getUserId()
        );
    }
    
    @GetMapping("/user/{userId}/groups")
    public List<GroupDto> getUserGroups(@PathVariable Long userId) {
        return groupMemberService.getGroupsByUserId(userId);
    }
    
    
    @GetMapping("/{groupId}/members/count")
    public long getMemberCount(@PathVariable Long groupId) {
        return groupMemberService.getMemberCount(groupId);
    }
    
    
    @GetMapping("/name/{groupName}/members")
    public List<GroupMemberDto> getMembersByGroupName(@PathVariable String groupName) {
        return groupMemberService.getMembersByGroupName(groupName);
    }

 
    @DeleteMapping("/{groupId}/members/{userId}")
    public SuccessMessageDto removeMember(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        groupMemberService.removeMember(groupId, userId);

        return new SuccessMessageDto(
                "Member removed successfully: userId ",
                userId
        );
    }

    
    @GetMapping("/{groupId}/members")
    public List<GroupMemberDto> getMembers(@PathVariable Long groupId) {
        return groupMemberService.getMembersByGroup(groupId);
    }
}