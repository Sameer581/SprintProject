import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { GroupMemberService } from '../../services/group-member.service';
import { AuthService } from '../../services/auth.service';
import { Group } from '../../models/group.model';

@Component({
  selector: 'app-group-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent implements OnInit {
  currentUserId: number | null = null;
  
  allGroups: Group[] = [];
  myGroups: Group[] = [];
  
  loadingAll = true;
  loadingMy = true;

  showCreateForm = false;
  newGroupName = '';
  isCreating = false;

  constructor(
    private groupService: GroupService,
    private groupMemberService: GroupMemberService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.loadAllGroups();
    this.loadMyGroups();
  }

  loadAllGroups() {
    this.loadingAll = true;
    this.groupService.getAllGroups().subscribe({
      next: (groups) => {
        this.allGroups = groups;
        this.loadingAll = false;
      },
      error: (err) => {
        console.error('Failed to load all groups', err);
        this.loadingAll = false;
      }
    });
  }

  loadMyGroups() {
    if (!this.currentUserId) return;
    this.loadingMy = true;
    this.groupMemberService.getGroupsByUser(this.currentUserId).subscribe({
      next: (groups) => {
        this.myGroups = groups;
        this.loadingMy = false;
      },
      error: (err) => {
        console.error('Failed to load my groups', err);
        this.loadingMy = false;
      }
    });
  }

  toggleCreateForm() {
    this.showCreateForm = !this.showCreateForm;
  }

  createGroup() {
    if (!this.currentUserId || !this.newGroupName.trim()) return;

    this.isCreating = true;
    this.groupService.createGroup({
      groupName: this.newGroupName,
      adminId: this.currentUserId
    }).subscribe({
      next: () => {
        this.loadMyGroups();
        this.loadAllGroups();
        
        this.newGroupName = '';
        this.isCreating = false;
        this.showCreateForm = false;
      },
      error: (err) => {
        console.error('Failed to create group', err);
        this.isCreating = false;
      }
    });
  }
}

