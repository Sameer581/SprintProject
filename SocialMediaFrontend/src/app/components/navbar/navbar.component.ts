import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { GroupService } from '../../services/group.service';
import { UserResponse } from '../../models/user-response.model';
import { GroupResponse } from '../../models/group-response.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  username = '';
  searchQuery = '';
  
  userResults: UserResponse[] = [];
  groupResults: GroupResponse[] = [];
  
  isSearching = false;
  showDropdown = false;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private userService: UserService,
    private groupService: GroupService
  ) {}

  ngOnInit(): void {
    this.username = localStorage.getItem('username') || '';
  }

  onSearchChange() {
    if (!this.searchQuery.trim()) {
      this.showDropdown = false;
      this.userResults = [];
      this.groupResults = [];
      return;
    }

    this.isSearching = true;
    this.showDropdown = true;

    // Search users
    this.userService.searchByUsername(this.searchQuery).subscribe({
      next: (users) => {
        this.userResults = users.slice(0, 5); // Limit to 5
        this.checkSearchComplete();
      },
      error: () => this.checkSearchComplete()
    });

    // Search groups
    this.groupService.searchGroups(this.searchQuery).subscribe({
      next: (groups) => {
        this.groupResults = groups.slice(0, 5); // Limit to 5
        this.checkSearchComplete();
      },
      error: () => this.checkSearchComplete()
    });
  }

  checkSearchComplete() {
    // Basic way to end loading state, could be improved with forkJoin
    setTimeout(() => { this.isSearching = false; }, 300);
  }

  onSubmit() {
    // If they hit enter, go to first user or first group
    if (this.userResults.length > 0) {
      this.goToProfile(this.userResults[0].userId);
    } else if (this.groupResults.length > 0) {
      this.goToGroup(this.groupResults[0].groupId);
    }
  }

  goToProfile(userId: number | undefined) {
    if (!userId) return;
    this.showDropdown = false;
    this.searchQuery = '';
    this.router.navigate(['/profile', userId]);
  }

  goToGroup(groupId: number | undefined) {
    if (!groupId) return;
    this.showDropdown = false;
    this.searchQuery = '';
    this.router.navigate(['/groups', groupId]);
  }

  hideDropdown() {
    setTimeout(() => {
      this.showDropdown = false;
    }, 200);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
