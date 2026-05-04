import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Notification } from '../models/notification.model';
import { NotificationResponse } from '../models/notification-response.model';
import { CreateNotification } from '../models/create-notification.model';
import { SuccessMessage } from '../models/success-message.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private baseUrl = 'http://localhost:8080/notifications';

  constructor(private http: HttpClient) {}

  // GET /notifications/{id}
  getNotification(id: number): Observable<NotificationResponse> {
    return this.http.get<NotificationResponse>(`${this.baseUrl}/${id}`);
  }

  // GET /notifications/viewall
  getAllNotifications(): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(`${this.baseUrl}/viewall`);
  }

  // POST /notifications/add
  addNotification(dto: CreateNotification): Observable<SuccessMessage> {
    return this.http.post<SuccessMessage>(`${this.baseUrl}/add`, dto);
  }

  // GET /notifications/user/{userId}
  getNotificationsByUser(userId: number): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(`${this.baseUrl}/user/${userId}`);
  }

  // GET /notifications/user/{userId}/count
  countNotificationsByUser(userId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/user/${userId}/count`);
  }

  // PUT /notifications/{id}
  updateNotification(id: number, dto: Notification): Observable<NotificationResponse> {
    return this.http.put<NotificationResponse>(`${this.baseUrl}/${id}`, dto);
  }

  // DELETE /notifications/{id}
  deleteNotification(id: number): Observable<SuccessMessage> {
    return this.http.delete<SuccessMessage>(`${this.baseUrl}/${id}`);
  }
}

