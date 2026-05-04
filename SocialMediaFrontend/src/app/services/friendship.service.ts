import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friendship } from '../models/friendship.model';
import { FriendshipRequest } from '../models/friendship-request.model';

@Injectable({
  providedIn: 'root'
})
export class FriendshipService {

  private baseUrl = 'http://localhost:8080/api/friendships';

  constructor(private http: HttpClient) {}

  // POST /api/friendships/request
  sendFriendRequest(dto: FriendshipRequest): Observable<Friendship> {
    return this.http.post<Friendship>(`${this.baseUrl}/request`, dto);
  }

  // PUT /api/friendships/{friendshipId}/accept
  acceptFriendRequest(friendshipId: number): Observable<Friendship> {
    return this.http.put<Friendship>(`${this.baseUrl}/${friendshipId}/accept`, {});
  }

  // DELETE /api/friendships/{friendshipId}/reject
  rejectFriendRequest(friendshipId: number): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}/${friendshipId}/reject`);
  }

  // GET /api/friendships/users/{userId}/pending
  getPendingRequests(userId: number): Observable<Friendship[]> {
    return this.http.get<Friendship[]>(`${this.baseUrl}/users/${userId}/pending`);
  }

  // GET /api/friendships/{friendshipId}
  getFriendshipById(friendshipId: number): Observable<Friendship> {
    return this.http.get<Friendship>(`${this.baseUrl}/${friendshipId}`);
  }

  // GET /api/friendships/users/{userId}/friends
  getFriendsByUserId(userId: number): Observable<Friendship[]> {
    return this.http.get<Friendship[]>(`${this.baseUrl}/users/${userId}/friends`);
  }

  // GET /api/friendships
  getAllFriendships(): Observable<Friendship[]> {
    return this.http.get<Friendship[]>(this.baseUrl);
  }

  // GET /api/friendships/check?userId1=&userId2=
  checkFriendship(userId1: number, userId2: number): Observable<Friendship> {
    const params = new HttpParams()
      .set('userId1', userId1.toString())
      .set('userId2', userId2.toString());
    return this.http.get<Friendship>(`${this.baseUrl}/check`, { params });
  }

  // GET /api/friendships/users/{userId}/friends/count
  getFriendsCount(userId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/users/${userId}/friends/count`);
  }
}

