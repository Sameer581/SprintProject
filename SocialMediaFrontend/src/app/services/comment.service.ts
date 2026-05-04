import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/comment-response.model';
import { CreateComment } from '../models/create-comment.model';
import { UpdateComment } from '../models/update-comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // POST /api/posts/{postId}/comments
  addComment(postId: number, dto: CreateComment): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(`${this.baseUrl}/posts/${postId}/comments`, dto);
  }

  // GET /api/posts/{postId}/comments
  getCommentsByPost(postId: number): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(`${this.baseUrl}/posts/${postId}/comments`);
  }

  // GET /api/comments/{id}
  getComment(id: number): Observable<CommentResponse> {
    return this.http.get<CommentResponse>(`${this.baseUrl}/comments/${id}`);
  }

  // PUT /api/comments/{id}
  updateComment(id: number, dto: UpdateComment): Observable<CommentResponse> {
    return this.http.put<CommentResponse>(`${this.baseUrl}/comments/${id}`, dto);
  }

  // DELETE /api/comments/{id}
  deleteComment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/comments/${id}`);
  }

  // GET /api/users/{userId}/comments
  getCommentsByUser(userId: number): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(`${this.baseUrl}/users/${userId}/comments`);
  }
}

