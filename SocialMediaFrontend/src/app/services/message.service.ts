import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../models/message.model';
import { MessageResponse } from '../models/message-response.model';
import { SuccessMessage } from '../models/success-message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080/messages';

  constructor(private http: HttpClient) {}

  // GET /messages/{id}
  getMessage(id: number): Observable<MessageResponse> {
    return this.http.get<MessageResponse>(`${this.baseUrl}/${id}`);
  }

  // GET /messages/viewall
  getAllMessages(): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.baseUrl}/viewall`);
  }

  // POST /messages/send
  sendMessage(dto: Message): Observable<SuccessMessage> {
    return this.http.post<SuccessMessage>(`${this.baseUrl}/send`, dto);
  }

  // GET /messages/sender/{senderId}
  getMessagesBySender(senderId: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.baseUrl}/sender/${senderId}`);
  }

  // GET /messages/receiver/{receiverId}
  getMessagesByReceiver(receiverId: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.baseUrl}/receiver/${receiverId}`);
  }

  // GET /messages/conversation/{user1}/{user2}
  getConversation(user1: number, user2: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.baseUrl}/conversation/${user1}/${user2}`);
  }

  // GET /messages/sender/{senderId}/count
  countMessagesBySender(senderId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/sender/${senderId}/count`);
  }

  // PUT /messages/{id}
  updateMessage(id: number, dto: Message): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.baseUrl}/${id}`, dto);
  }
}

