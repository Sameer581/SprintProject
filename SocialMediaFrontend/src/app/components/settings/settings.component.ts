import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit {
  userId: number | null = null;
  
  // Username Form
  currentUsername = '';
  newUsername = '';
  usernameSuccess = '';
  usernameError = '';
  isUpdatingUsername = false;

  // Email Form
  currentEmail = '';
  newEmail = '';
  emailSuccess = '';
  emailError = '';
  isUpdatingEmail = false;

  // Password Form
  newPassword = '';
  confirmPassword = '';
  passwordSuccess = '';
  passwordError = '';
  isUpdatingPassword = false;

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUserId();
    this.loadUserData();
  }

  loadUserData() {
    if (!this.userId) return;
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.currentUsername = user.username;
        this.currentEmail = user.email;
        // Optionally update localStorage if it changed
        localStorage.setItem('username', user.username);
        localStorage.setItem('email', user.email);
      },
      error: (err) => console.error('Failed to load user data', err)
    });
  }

  updateUsername() {
    if (!this.userId || !this.newUsername.trim()) return;
    this.isUpdatingUsername = true;
    this.usernameSuccess = '';
    this.usernameError = '';

    this.userService.updateUsername(this.userId, this.newUsername).subscribe({
      next: (res) => {
        this.usernameSuccess = 'Username updated successfully!';
        this.isUpdatingUsername = false;
        this.newUsername = '';
        this.loadUserData(); // Refresh current username
      },
      error: (err) => {
        this.usernameError = 'Failed to update username. It might be taken.';
        this.isUpdatingUsername = false;
      }
    });
  }

  updateEmail() {
    if (!this.userId || !this.newEmail.trim()) return;
    this.isUpdatingEmail = true;
    this.emailSuccess = '';
    this.emailError = '';

    this.userService.updateEmail(this.userId, this.newEmail).subscribe({
      next: (res) => {
        this.emailSuccess = 'Email updated successfully!';
        this.isUpdatingEmail = false;
        this.newEmail = '';
        this.loadUserData(); // Refresh current email
      },
      error: (err) => {
        this.emailError = 'Failed to update email. It might be taken or invalid.';
        this.isUpdatingEmail = false;
      }
    });
  }

  updatePassword() {
    if (!this.userId || !this.newPassword) return;
    if (this.newPassword !== this.confirmPassword) {
      this.passwordError = 'Passwords do not match.';
      return;
    }

    this.isUpdatingPassword = true;
    this.passwordSuccess = '';
    this.passwordError = '';

    this.userService.updatePassword(this.userId, this.newPassword).subscribe({
      next: (res) => {
        this.passwordSuccess = 'Password updated successfully!';
        this.isUpdatingPassword = false;
        this.newPassword = '';
        this.confirmPassword = '';
      },
      error: (err) => {
        this.passwordError = 'Failed to update password.';
        this.isUpdatingPassword = false;
      }
    });
  }
}
