package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupSummaryDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.exception.ValidationException;
import com.cg.service.GroupService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{groupId}")
    public GroupDto getGroup(@PathVariable Long groupId) {
        return groupService.getGroupById(groupId);
    }

    @GetMapping("/viewall")
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addGroup(
            @Valid @RequestBody GroupDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        GroupDto saved = groupService.createGroup(dto);

        return new SuccessMessageDto(
                "Group created successfully with id ",
                saved.getGroupId()
        );
    }

    @PutMapping("/update/{groupId}")
    public SuccessMessageDto updateGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        GroupDto updated = groupService.updateGroup(groupId, dto);

        return new SuccessMessageDto(
                "Group updated successfully with id ",
                updated.getGroupId()
        );
    }

    @DeleteMapping("/delete/{groupId}")
    public SuccessMessageDto deleteGroup(@PathVariable Long groupId) {

        groupService.deleteGroup(groupId);

        return new SuccessMessageDto(
                "Group deleted successfully with id ",
                groupId
        );
    }

    @GetMapping("/name/{groupName}")
    public GroupDto getByName(@PathVariable String groupName) {
        return groupService.getGroupByName(groupName);
    }

    @GetMapping("/admin/{adminId}")
    public List<GroupDto> getByAdmin(@PathVariable Long adminId) {
        return groupService.getGroupsByAdminId(adminId);
    }
    
 
    @GetMapping("/{groupId}/summary")
    public GroupSummaryDto getGroupSummary(@PathVariable Long groupId) {
        return groupService.getGroupSummary(groupId);
    }
    
    @GetMapping("/search")
    public List<GroupDto> searchGroups(@RequestParam String keyword) {
        return groupService.searchGroupsByName(keyword);
    }

    
    
}





