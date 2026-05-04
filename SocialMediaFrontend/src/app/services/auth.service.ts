import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap, switchMap } from 'rxjs/operators';
import { UserService } from './user.service';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient, private userService: UserService) {}

  login(data: any) {
    return this.http.post<any>(`${this.baseUrl}/login`, data).pipe(
      switchMap(res => {
        localStorage.setItem('token', res.token);
        const payload = JSON.parse(atob(res.token.split('.')[1]));
        const email = payload.sub;
        return this.userService.getUserByEmail(email).pipe(
          tap(user => {
            localStorage.setItem('userId', user.userId.toString());
            localStorage.setItem('username', user.username);
            localStorage.setItem('email', user.email);
          })
        );
      })
    );
  }

  register(data: any) {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getCurrentUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
    localStorage.removeItem('email');
  }

  isLoggedIn() {
    return !!this.getToken();
  }
}