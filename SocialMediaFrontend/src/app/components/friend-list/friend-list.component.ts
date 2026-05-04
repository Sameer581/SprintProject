import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FriendshipService } from '../../services/friendship.service';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { Friendship } from '../../models/friendship.model';

@Component({
  selector: 'app-friend-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './friend-list.component.html',
  styleUrl: './friend-list.component.css'
})
export class FriendListComponent implements OnInit {
  currentUserId: number | null = null;
  friends: Friendship[] = [];
  pendingRequests: Friendship[] = [];
  
  loadingFriends = true;
  loadingRequests = true;

  constructor(
    private friendshipService: FriendshipService,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.loadFriends();
    this.loadRequests();
  }

  loadFriends() {
    if (!this.currentUserId) return;
    this.loadingFriends = true;
    this.friendshipService.getFriendsByUserId(this.currentUserId).subscribe({
      next: (friends) => {
        this.friends = friends;
        this.loadingFriends = false;
      },
      error: (err) => {
        console.error('Failed to load friends', err);
        this.loadingFriends = false;
      }
    });
  }

  loadRequests() {
    if (!this.currentUserId) return;
    this.loadingRequests = true;
    this.friendshipService.getPendingRequests(this.currentUserId).subscribe({
      next: (requests) => {
        // Pending requests received by the current user
        this.pendingRequests = requests;
        this.loadingRequests = false;
      },
      error: (err) => {
        console.error('Failed to load requests', err);
        this.loadingRequests = false;
      }
    });
  }

  acceptRequest(friendshipId: number | undefined) {
    if (!friendshipId) return;
    this.friendshipService.acceptFriendRequest(friendshipId).subscribe({
      next: () => {
        this.loadRequests();
        this.loadFriends();
      },
      error: (err) => console.error('Failed to accept request', err)
    });
  }

  rejectRequest(friendshipId: number | undefined) {
    if (!friendshipId) return;
    this.friendshipService.rejectFriendRequest(friendshipId).subscribe({
      next: () => {
        this.loadRequests();
      },
      error: (err) => console.error('Failed to reject request', err)
    });
  }
}

