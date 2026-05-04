import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LikeButtonComponent } from '../like-button/like-button.component';
import { Post } from '../../models/post.model';
import { CommentService } from '../../services/comment.service';
import { CommentResponse } from '../../models/comment-response.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, LikeButtonComponent],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.css'
})
export class PostCardComponent implements OnInit {
  @Input() post!: Post;

  showComments = false;
  comments: CommentResponse[] = [];
  loadingComments = false;
  
  newCommentText = '';
  isSubmittingComment = false;

  constructor(
    private commentService: CommentService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Optionally load comment count here if backend provides it in the future
  }

  toggleComments() {
    this.showComments = !this.showComments;
    if (this.showComments && this.comments.length === 0) {
      this.loadComments();
    }
  }

  loadComments() {
    if (!this.post || !this.post.postId) return;
    this.loadingComments = true;
    this.commentService.getCommentsByPost(this.post.postId).subscribe({
      next: (comments) => {
        // Sort newest first or oldest first? Usually oldest first for comments (top down)
        this.comments = comments.sort((a, b) => {
          const dateA = a.timestamp ? new Date(a.timestamp).getTime() : 0;
          const dateB = b.timestamp ? new Date(b.timestamp).getTime() : 0;
          return dateA - dateB;
        });
        this.loadingComments = false;
      },
      error: (err) => {
        console.error('Failed to load comments', err);
        this.loadingComments = false;
      }
    });
  }

  submitComment() {
    if (!this.newCommentText.trim() || !this.post.postId) return;

    const userId = this.authService.getCurrentUserId();
    if (!userId) {
      alert('You must be logged in to comment');
      return;
    }

    this.isSubmittingComment = true;
    this.commentService.addComment(this.post.postId, {
      userId: userId,
      postId: this.post.postId,
      commentText: this.newCommentText
    }).subscribe({
      next: (newComment) => {
        this.newCommentText = '';
        this.isSubmittingComment = false;
        // Optionally push directly to array to avoid full reload
        this.comments.push(newComment);
      },
      error: (err) => {
        console.error('Failed to add comment', err);
        this.isSubmittingComment = false;
      }
    });
  }
}

