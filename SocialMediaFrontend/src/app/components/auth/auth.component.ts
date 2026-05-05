import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {

  // Toggle: 'login' | 'register'
  mode: 'login' | 'register' = 'login';

  // Login fields
  email = '';
  password = '';

  // Register fields
  username = '';
  custName = '';
  phoneNo = '';
  regEmail = '';
  regPassword = '';

  errorMsg = '';
  successMsg = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  switchTo(m: 'login' | 'register') {
    this.mode = m;
    this.errorMsg = '';
    this.successMsg = '';
  }

  login() {
    this.errorMsg = '';
    this.authService.login({
      email: this.email,
      password: this.password
    }).subscribe({
      next: () => {
        this.router.navigate(['/feed']);
      },
      error: (err) => {
        this.errorMsg = 'Login failed. Please check your credentials.';
        console.error('Login failed', err);
      }
    });
  }

  register() {
    this.errorMsg = '';
    this.successMsg = '';
    this.authService.register({
      username: this.username,
      custName: this.custName,
      phoneNo: this.phoneNo,
      email: this.regEmail,
      password: this.regPassword
    }).subscribe({
      next: () => {
        this.switchTo('login');
        this.successMsg = 'Account created successfully! Please log in.';
      },
      error: (err) => {
        this.errorMsg = 'Registration failed. Please try again.';
        console.error('Register failed', err);
      }
    });
  }
}