import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
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
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMsg = 'Registration failed. Please try again.';
        console.error('Register failed', err);
      }
    });
  }
}
