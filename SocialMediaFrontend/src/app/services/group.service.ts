import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Group } from '../models/group.model';
import { GroupResponse } from '../models/group-response.model';
import { GroupSummary } from '../models/group-summary.model';
import { CreateGroup } from '../models/create-group.model';
import { UpdateGroup } from '../models/update-group.model';
import { SuccessMessage } from '../models/success-message.model';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private baseUrl = 'http://localhost:8080/groups';

  constructor(private http: HttpClient) {}

  // GET /groups/{groupId}
  getGroup(groupId: number): Observable<GroupResponse> {
    return this.http.get<GroupResponse>(`${this.baseUrl}/${groupId}`);
  }

  // GET /groups/viewall
  getAllGroups(): Observable<GroupResponse[]> {
    return this.http.get<GroupResponse[]>(`${this.baseUrl}/viewall`);
  }

  // POST /groups/add
  createGroup(dto: CreateGroup): Observable<SuccessMessage> {
    return this.http.post<SuccessMessage>(`${this.baseUrl}/add`, dto);
  }

  // PUT /groups/update/{groupId}
  updateGroup(groupId: number, dto: UpdateGroup): Observable<SuccessMessage> {
    return this.http.put<SuccessMessage>(`${this.baseUrl}/update/${groupId}`, dto);
  }

  // DELETE /groups/delete/{groupId}
  deleteGroup(groupId: number): Observable<SuccessMessage> {
    return this.http.delete<SuccessMessage>(`${this.baseUrl}/delete/${groupId}`);
  }

  // GET /groups/name/{groupName}
  getGroupByName(groupName: string): Observable<GroupResponse> {
    return this.http.get<GroupResponse>(`${this.baseUrl}/name/${groupName}`);
  }

  // GET /groups/admin/{adminId}
  getGroupsByAdmin(adminId: number): Observable<GroupResponse[]> {
    return this.http.get<GroupResponse[]>(`${this.baseUrl}/admin/${adminId}`);
  }

  // GET /groups/{groupId}/summary
  getGroupSummary(groupId: number): Observable<GroupSummary> {
    return this.http.get<GroupSummary>(`${this.baseUrl}/${groupId}/summary`);
  }

  // GET /groups/search?keyword=
  searchGroups(keyword: string): Observable<GroupResponse[]> {
    const params = new HttpParams().set('keyword', keyword);
    return this.http.get<GroupResponse[]>(`${this.baseUrl}/search`, { params });
  }
}

