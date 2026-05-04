import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/post.model';
import { CreatePost } from '../models/create-post.model';
import { UpdatePost } from '../models/update-post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl = 'http://localhost:8080/posts';

  constructor(public http: HttpClient) {}

  // GET /posts
  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.baseUrl);
  }

  // GET /posts/{id}
  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.baseUrl}/${id}`);
  }

  // POST /posts
  createPost(dto: CreatePost): Observable<Post> {
    return this.http.post<Post>(this.baseUrl, dto);
  }

  // PUT /posts/{id}
  updatePost(id: number, dto: UpdatePost): Observable<Post> {
    return this.http.put<Post>(`${this.baseUrl}/${id}`, dto);
  }

  // GET /posts/user/{userId}
  getPostsByUser(userId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseUrl}/user/${userId}`);
  }
}

