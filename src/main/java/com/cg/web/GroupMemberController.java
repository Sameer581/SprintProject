package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDetailDto;
import com.cg.dto.GroupMemberDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.dto.UpdateRoleDto;
import com.cg.exception.ValidationException;
import com.cg.service.GroupMemberService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/groups")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    /*
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
    }*/
    @PostMapping("/{groupId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addMember(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        GroupMemberDto saved = groupMemberService.addMember(groupId, dto);

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
    
 
    @GetMapping("/{groupId}/members/details")
    public List<GroupMemberDetailDto> getMemberDetails(@PathVariable Long groupId) {
        return groupMemberService.getMemberDetails(groupId);
    }

    
    @GetMapping("/{groupId}/members/role/{role}")
    public List<GroupMemberDetailDto> getMembersByRole(
            @PathVariable Long groupId,
            @PathVariable String role) {
        return groupMemberService.getMembersByRole(groupId, role);
    }

    
    @PatchMapping("/{groupId}/members/{userId}/role")
    public SuccessMessageDto updateMemberRole(
            @PathVariable Long groupId,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRoleDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        GroupMemberDto updated = groupMemberService.updateMemberRole(groupId, userId, dto.getRole());

        return new SuccessMessageDto(
                "Role updated successfully for userId ",
                updated.getUserId()
        );
    }

    
    @GetMapping("/{groupId}/members/search")
    public List<GroupMemberDetailDto> searchMembers(
            @PathVariable Long groupId,
            @RequestParam String name) {
        return groupMemberService.searchMembers(groupId, name);
    }
    
    @PostMapping("/{groupId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto requestToJoin(
            @PathVariable Long groupId,
            @RequestParam Long userId) {

        GroupMemberDto saved = groupMemberService.requestToJoin(groupId, userId);

        return new SuccessMessageDto(
                "Join request sent successfully for userId ",
                saved.getUserId()
        );
    }

    @DeleteMapping("/{groupId}/leave/{userId}")
    public SuccessMessageDto leaveGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        groupMemberService.leaveGroup(groupId, userId);

        return new SuccessMessageDto(
                "User left the group successfully, userId ",
                userId
        );
    }

    @GetMapping("/{groupId}/requests")
    public List<GroupMemberDto> getPendingRequests(@PathVariable Long groupId) {
        return groupMemberService.getPendingRequests(groupId);
    }

    @PatchMapping("/{groupId}/requests/{userId}/approve")
    public SuccessMessageDto approveRequest(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        GroupMemberDto gm = groupMemberService.approveRequest(groupId, userId);

        return new SuccessMessageDto(
                "Join request approved for userId ",
                gm.getUserId()
        );
    }

    @PatchMapping("/{groupId}/requests/{userId}/reject")
    public SuccessMessageDto rejectRequest(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        GroupMemberDto gm = groupMemberService.rejectRequest(groupId, userId);

        return new SuccessMessageDto(
                "Join request rejected for userId ",
                gm.getUserId()
        );
    }
    
    @GetMapping("/name/{groupName}/members/search")
    public List<GroupMemberDetailDto> searchMembersByGroupName(
            @PathVariable String groupName,
            @RequestParam String keyword) {
        return groupMemberService.searchMembersByGroupName(groupName, keyword);
    }
    
    
    
}