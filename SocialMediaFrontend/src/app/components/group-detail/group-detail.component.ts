import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { GroupMemberService } from '../../services/group-member.service';
import { AuthService } from '../../services/auth.service';
import { Group } from '../../models/group.model';
import { GroupMember } from '../../models/group-member.model';

@Component({
  selector: 'app-group-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './group-detail.component.html',
  styleUrl: './group-detail.component.css'
})
export class GroupDetailComponent implements OnInit {
  currentUserId: number | null = null;
  groupId: number | null = null;
  
  group: Group | null = null;
  memberStatus: GroupMember | null = null; // null if not a member/requested
  
  pendingRequests: GroupMember[] = [];
  activeMembers: GroupMember[] = [];
  searchQuery = '';
  
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
        this.activeMembers = members;
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
        this.activeMembers = [];
        this.checkIfPending();
      }
    });
  }

  searchMembers() {
    if (!this.groupId) return;
    
    if (!this.searchQuery.trim()) {
      this.checkMembershipStatus();
      return;
    }

    // Call the search endpoint
    this.groupMemberService.searchMembers(this.groupId, this.searchQuery.trim()).subscribe({
      next: (results) => {
        // Map GroupMemberDetail to GroupMember structure for the UI
        this.activeMembers = results.map(r => ({
           userId: r.userId,
           role: r.role,
           status: 'ACTIVE',
           username: r.name // GroupMemberDetail has 'name', GroupMember expects 'username'
        })) as any;
      },
      error: (err) => {
        console.error('Search failed', err);
        this.activeMembers = [];
      }
    });
  }

  makeAdmin(userId: number) {
    if (!this.groupId || !this.currentUserId || this.memberStatus?.role !== 'ADMIN') return;
    
    if (confirm('Are you sure you want to make this user an Admin?')) {
      this.groupMemberService.updateMemberRole(this.groupId, userId, { role: 'ADMIN' }).subscribe({
        next: () => {
          this.checkMembershipStatus(); // Reload members to show updated roles
        },
        error: (err) => {
          console.error('Failed to update role', err);
          alert('Failed to promote user to Admin.');
        }
      });
    }
  }

  transferOwnership(userId: number) {
    if (!this.groupId || !this.currentUserId || this.group?.adminId !== this.currentUserId) return;
    
    if (confirm('Are you sure you want to transfer GROUP OWNERSHIP to this user? You will become a regular member.')) {
      this.groupMemberService.transferAdmin(this.groupId, this.currentUserId, userId).subscribe({
        next: () => {
          this.loadGroupDetails(); // Reload everything to update group.adminId
        },
        error: (err) => {
          console.error('Failed to transfer ownership', err);
          if (err.status === 404) {
             alert('ERROR 404: The Transfer Admin endpoint was not found. Please completely STOP and RESTART your Spring Boot backend so the new endpoint is loaded!');
          } else {
             alert('Failed to transfer group ownership. Ensure your backend is restarted. Status: ' + err.status);
          }
        }
      });
    }
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
        this.checkMembershipStatus(); // Reload to update the member list
      },
      error: (err) => {
        console.error('Failed to leave group', err);
        this.actionLoading = false;
        
        // The backend throws a ValidationException if an Admin tries to leave
        // ValidationException is mapped to { fieldName: [errors...] }
        const errStr = JSON.stringify(err.error || {});
        if (errStr.includes('Admin')) {
          alert('Admin cannot leave the group. You must click "Transfer Ownership" to someone else first!');
        } else {
          alert('Failed to leave the group. Please try again later.');
        }
      }
    });
  }

  approveRequest(userId: number | undefined) {
    if (!userId || !this.groupId) return;
    this.groupMemberService.approveRequest(this.groupId, userId).subscribe({
      next: () => {
        this.pendingRequests = this.pendingRequests.filter(r => r.userId !== userId);
        this.checkMembershipStatus(); // Reload active members list
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

