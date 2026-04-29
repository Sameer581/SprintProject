/*package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.GroupDto;
import com.cg.service.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto) {
        return new ResponseEntity<>(groupService.createGroup(groupDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) {  
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @GetMapping("/name/{groupName}")
    public ResponseEntity<GroupDto> getGroupByName(@PathVariable String groupName) {
        return new ResponseEntity<>(groupService.getGroupByName(groupName), HttpStatus.OK);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<GroupDto>> getGroupsByAdmin(@PathVariable Long adminId) { 
        return new ResponseEntity<>(groupService.getGroupsByAdminId(adminId), HttpStatus.OK);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long groupId,  
            @Valid @RequestBody GroupDto groupDto) {
        return new ResponseEntity<>(groupService.updateGroup(groupId, groupDto), HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {  
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
    }
}*/



package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.GroupDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.exception.ValidationException;
import com.cg.service.GroupService;

import jakarta.validation.Valid;

@RestController
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
}








