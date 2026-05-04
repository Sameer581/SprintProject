import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponse } from '../models/user-response.model';
import { UpdateUser } from '../models/update-user.model';
import { SuccessMessage } from '../models/success-message.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {}

  // POST /users/register
  registerUser(dto: UpdateUser): Observable<SuccessMessage> {
    return this.http.post<SuccessMessage>(`${this.baseUrl}/register`, dto);
  }

  // GET /users/{id}
  getUserById(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/${id}`);
  }

  // GET /users/all
  getAllUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${this.baseUrl}/all`);
  }

  // GET /users/search?username=
  searchByUsername(username: string): Observable<UserResponse[]> {
    const params = new HttpParams().set('username', username);
    return this.http.get<UserResponse[]>(`${this.baseUrl}/search`, { params });
  }

  // GET /users/email?email=
  getUserByEmail(email: string): Observable<UserResponse> {
    const params = new HttpParams().set('email', email);
    return this.http.get<UserResponse>(`${this.baseUrl}/email`, { params });
  }

  // GET /users/username/{username}
  getUserByUsername(username: string): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/username/${username}`);
  }

  // GET /users/count
  countUsers(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count`);
  }

  // PUT /users/{id}
  updateUser(id: number, dto: UpdateUser): Observable<UserResponse> {
    return this.http.put<UserResponse>(`${this.baseUrl}/${id}`, dto);
  }

  // PATCH /users/{id}/username?newUsername=
  updateUsername(id: number, newUsername: string): Observable<SuccessMessage> {
    const params = new HttpParams().set('newUsername', newUsername);
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${id}/username`, {}, { params });
  }

  // PATCH /users/{id}/email?newEmail=
  updateEmail(id: number, newEmail: string): Observable<SuccessMessage> {
    const params = new HttpParams().set('newEmail', newEmail);
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${id}/email`, {}, { params });
  }

  // PATCH /users/{id}/password?newPassword=
  updatePassword(id: number, newPassword: string): Observable<SuccessMessage> {
    const params = new HttpParams().set('newPassword', newPassword);
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${id}/password`, {}, { params });
  }

  // DELETE /users/{id}
  deleteUser(id: number): Observable<SuccessMessage> {
    return this.http.delete<SuccessMessage>(`${this.baseUrl}/${id}`);
  }
}

