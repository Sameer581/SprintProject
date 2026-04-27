package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.CommentsDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.entity.Comments;
import com.cg.exception.ValidationException;
import com.cg.service.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/{id}")
    public Comments getComment(@PathVariable Long id) {
        return commentsService.getComment(id);
    }

    @GetMapping("/viewall")
    public List<Comments> getAllComments() {
        return commentsService.getAllComments();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addComment(@Valid @RequestBody CommentsDto dto, BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        Long cid = commentsService.addComment(dto);

        return new SuccessMessageDto("Comment added successfully with id ", cid);
    }
}