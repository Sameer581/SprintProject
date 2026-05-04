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

  email = '';
  password = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  login() {
  this.authService.login({
    email: this.email,
    password: this.password
  }).subscribe({
    next: () => {
      this.router.navigate(['/feed']);
    },
    error: (err) => {
      console.error('Login failed', err);
    }
  });
}
}