import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Like } from '../models/like.model';
import { LikeResponse } from '../models/like-response.model';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  private baseUrl = 'http://localhost:8080/likes';

  constructor(private http: HttpClient) {}

  // POST /likes/toggle  { userId, postId }
  toggleLike(dto: Like): Observable<LikeResponse> {
    return this.http.post<LikeResponse>(`${this.baseUrl}/toggle`, dto);
  }

  // GET /likes/count/{postId}
  countLikesByPost(postId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count/${postId}`);
  }

  // GET /likes/check/{userId}/{postId}
  hasUserLiked(userId: number, postId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/check/${userId}/${postId}`);
  }

  // GET /likes/post/{postId}
  getLikesByPost(postId: number): Observable<LikeResponse[]> {
    return this.http.get<LikeResponse[]>(`${this.baseUrl}/post/${postId}`);
  }

  // GET /likes/user/{userId}
  getLikesByUser(userId: number): Observable<LikeResponse[]> {
    return this.http.get<LikeResponse[]>(`${this.baseUrl}/user/${userId}`);
  }

  // DELETE /likes/remove/{userId}/{postId}
  removeLike(userId: number, postId: number): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}/remove/${userId}/${postId}`);
  }

  // GET /likes/count/user/{userId}
  countLikesByUser(userId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count/user/${userId}`);
  }
}

