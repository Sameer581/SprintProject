import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LikeService } from '../../services/like.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-like-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './like-button.component.html',
  styleUrl: './like-button.component.css'
})
export class LikeButtonComponent implements OnInit {
  @Input() postId!: number;
  
  isLiked = false;
  likeCount = 0;
  userId: number | null = null;

  constructor(
    private likeService: LikeService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUserId();
    this.loadLikeData();
  }

  loadLikeData() {
    if (!this.postId) return;
    
    this.likeService.countLikesByPost(this.postId).subscribe({
      next: (count) => this.likeCount = count,
      error: (err) => console.error('Failed to get like count', err)
    });

    if (this.userId) {
      this.likeService.hasUserLiked(this.userId, this.postId).subscribe({
        next: (liked) => this.isLiked = liked,
        error: (err) => console.error('Failed to check like status', err)
      });
    }
  }

  toggleLike() {
    if (!this.userId) return;

    this.likeService.toggleLike({ userId: this.userId, postId: this.postId }).subscribe({
      next: (response) => {
        // Toggle optimistic or reload
        this.loadLikeData();
      },
      error: (err) => console.error('Failed to toggle like', err)
    });
  }
}

