import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { PostService } from '../../services/post.service';
import { FriendshipService } from '../../services/friendship.service';
import { AuthService } from '../../services/auth.service';
import { UserResponse } from '../../models/user-response.model';
import { Post } from '../../models/post.model';
import { PostCardComponent } from '../post-card/post-card.component';
import { Friendship } from '../../models/friendship.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterModule, PostCardComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userId: number | null = null;
  currentLoggedInUserId: number | null = null;
  isOwnProfile = false;

  user: UserResponse | null = null;
  posts: Post[] = [];
  friendshipStatus: 'none' | 'pending' | 'accepted' = 'none';

  loading = true;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private postService: PostService,
    private friendshipService: FriendshipService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentLoggedInUserId = this.authService.getCurrentUserId();
    
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.userId = parseInt(idParam, 10);
      } else {
        this.userId = this.currentLoggedInUserId;
      }
      
      this.isOwnProfile = this.userId === this.currentLoggedInUserId;
      this.loadProfileData();
    });
  }

  loadProfileData() {
    if (!this.userId) return;
    this.loading = true;

    // Load User info
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.user = user;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load user', err);
        this.loading = false;
      }
    });

    // Load Posts
    this.postService.getPostsByUser(this.userId).subscribe({
      next: (posts) => {
        this.posts = posts.sort((a, b) => {
          const dateA = a.timestamp ? new Date(a.timestamp).getTime() : 0;
          const dateB = b.timestamp ? new Date(b.timestamp).getTime() : 0;
          return dateB - dateA;
        });
      },
      error: (err) => console.error('Failed to load posts', err)
    });

    // Check Friendship status if it's someone else
    if (!this.isOwnProfile && this.currentLoggedInUserId) {
      this.friendshipService.checkFriendship(this.currentLoggedInUserId, this.userId).subscribe({
        next: (friendship) => {
          if (friendship && friendship.status) {
            this.friendshipStatus = friendship.status.toLowerCase() as any;
          } else {
            this.friendshipStatus = 'none';
          }
        },
        error: (err) => {
          // If backend throws 404 for no friendship, handle it gracefully
          this.friendshipStatus = 'none';
        }
      });
    }
  }

  sendFriendRequest() {
    if (!this.currentLoggedInUserId || !this.userId) return;
    
    this.friendshipService.sendFriendRequest({
      userId1: this.currentLoggedInUserId,
      userId2: this.userId
    }).subscribe({
      next: (res) => {
        this.friendshipStatus = 'pending';
      },
      error: (err) => console.error('Failed to send friend request', err)
    });
  }
}

