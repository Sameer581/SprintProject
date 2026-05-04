import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-post-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-create.component.html',
  styleUrl: './post-create.component.css'
})
export class PostCreateComponent {
  content = '';
  isSubmitting = false;

  @Output() postCreated = new EventEmitter<void>();

  constructor(
    private postService: PostService,
    private authService: AuthService
  ) {}

  submitPost() {
    if (!this.content.trim()) return;
    
    const userId = this.authService.getCurrentUserId();
    if (!userId) {
      alert('You must be logged in to post.');
      return;
    }

    this.isSubmitting = true;
    this.postService.createPost({
      userId: userId,
      content: this.content
    }).subscribe({
      next: (post) => {
        this.content = '';
        this.isSubmitting = false;
        this.postCreated.emit();
      },
      error: (err) => {
        console.error('Failed to create post', err);
        this.isSubmitting = false;
        alert('Failed to create post');
      }
    });
  }
}

