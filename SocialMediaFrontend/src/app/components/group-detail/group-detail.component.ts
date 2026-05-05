import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { GroupMemberService } from '../../services/group-member.service';
import { AuthService } from '../../services/auth.service';
import { Group } from '../../models/group.model';
import { GroupMember } from '../../models/group-member.model';

@Component({
  selector: 'app-group-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './group-detail.component.html',
  styleUrl: './group-detail.component.css'
})
export class GroupDetailComponent implements OnInit {
  currentUserId: number | null = null;
  groupId: number | null = null;
  
  group: Group | null = null;
  memberStatus: GroupMember | null = null; // null if not a member/requested
  
  pendingRequests: GroupMember[] = [];
  
  loading = true;
  actionLoading = false;

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private groupMemberService: GroupMemberService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.route.paramMap.subscribe(params => {
      const idStr = params.get('id');
      if (idStr) {
        this.groupId = +idStr;
        this.loadGroupDetails();
      }
    });
  }

  loadGroupDetails() {
    if (!this.groupId) return;
    this.loading = true;

    // Load Group Info
    this.groupService.getGroup(this.groupId).subscribe({
      next: (g) => {
        this.group = g;
        this.checkMembershipStatus();
      },
      error: (err) => {
        console.error('Failed to load group', err);
        this.loading = false;
      }
    });
  }

  checkMembershipStatus() {
    if (!this.groupId || !this.currentUserId) {
      this.loading = false;
      return;
    }

    this.groupMemberService.getMembers(this.groupId).subscribe({
      next: (members) => {
        const me = members.find(m => m.userId === this.currentUserId);
        if (me) {
          this.memberStatus = me;
          if (me.role === 'ADMIN') {
            this.loadPendingRequests();
          } else {
            this.loading = false;
          }
        } else {
          // Not an active member, check if they have a pending request
          this.checkIfPending();
        }
      },
      error: (err) => {
        // Backend returns 404 if no members are active. Check pending anyway.
        this.checkIfPending();
      }
    });
  }

  checkIfPending() {
    if (!this.groupId || !this.currentUserId) return;
    this.groupMemberService.getPendingRequests(this.groupId).subscribe({
      next: (requests) => {
        const mePending = requests.find(r => r.userId === this.currentUserId);
        if (mePending) {
          this.memberStatus = { userId: this.currentUserId!, role: 'MEMBER', status: 'PENDING' };
        } else {
          this.memberStatus = null;
        }
        this.loading = false;
      },
      error: (err) => {
        this.memberStatus = null;
        this.loading = false;
      }
    });
  }

  loadPendingRequests() {
    if (!this.groupId) return;
    this.groupMemberService.getPendingRequests(this.groupId).subscribe({
      next: (requests) => {
        this.pendingRequests = requests;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load pending requests', err);
        this.loading = false;
      }
    });
  }

  requestToJoin() {
    if (!this.groupId || !this.currentUserId) return;
    this.actionLoading = true;
    
    this.groupMemberService.requestToJoin(this.groupId, this.currentUserId).subscribe({
      next: () => {
        this.checkMembershipStatus(); // reload status
        this.actionLoading = false;
      },
      error: (err) => {
        console.error('Failed to join', err);
        this.actionLoading = false;
        alert('Failed to send request. You may have already requested to join or the backend encountered an error.');
      }
    });
  }

  leaveGroup() {
    if (!this.groupId || !this.currentUserId || !this.memberStatus) return;
    this.actionLoading = true;
    
    // Fallback if leaveGroup requires ID or userId
    this.groupMemberService.leaveGroup(this.groupId, this.currentUserId).subscribe({
      next: () => {
        this.memberStatus = null;
        this.actionLoading = false;
      },
      error: (err) => {
        console.error('Failed to leave group', err);
        this.actionLoading = false;
      }
    });
  }

  approveRequest(userId: number | undefined) {
    if (!userId || !this.groupId) return;
    this.groupMemberService.approveRequest(this.groupId, userId).subscribe({
      next: () => {
        this.pendingRequests = this.pendingRequests.filter(r => r.userId !== userId);
      },
      error: (err) => console.error('Failed to approve request', err)
    });
  }

  rejectRequest(userId: number | undefined) {
    if (!userId || !this.groupId) return;
    this.groupMemberService.rejectRequest(this.groupId, userId).subscribe({
      next: () => {
        this.pendingRequests = this.pendingRequests.filter(r => r.userId !== userId);
      },
      error: (err) => console.error('Failed to reject request', err)
    });
  }
}

