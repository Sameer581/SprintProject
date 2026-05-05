import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupMember } from '../models/group-member.model';
import { GroupMemberDetail } from '../models/group-member-detail.model';
import { GroupMemberRequest } from '../models/group-member-request.model';
import { GroupMemberRole } from '../models/group-member-role.model';
import { GroupResponse } from '../models/group-response.model';
import { SuccessMessage } from '../models/success-message.model';

@Injectable({
  providedIn: 'root'
})
export class GroupMemberService {

  private baseUrl = 'http://localhost:8080/groups';

  constructor(private http: HttpClient) {}

  // POST /groups/{groupId}/members
  addMember(groupId: number, dto: GroupMemberRequest): Observable<SuccessMessage> {
    return this.http.post<SuccessMessage>(`${this.baseUrl}/${groupId}/members`, dto);
  }

  // GET /groups/user/{userId}/groups
  getGroupsByUser(userId: number): Observable<GroupResponse[]> {
    return this.http.get<GroupResponse[]>(`${this.baseUrl}/user/${userId}/groups`);
  }

  // GET /groups/{groupId}/members/count
  getMemberCount(groupId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/${groupId}/members/count`);
  }

  // GET /groups/name/{groupName}/members
  getMembersByGroupName(groupName: string): Observable<GroupMember[]> {
    return this.http.get<GroupMember[]>(`${this.baseUrl}/name/${groupName}/members`);
  }

  // DELETE /groups/{groupId}/members/{userId}
  removeMember(groupId: number, userId: number): Observable<SuccessMessage> {
    return this.http.delete<SuccessMessage>(`${this.baseUrl}/${groupId}/members/${userId}`);
  }

  // GET /groups/{groupId}/members
  getMembers(groupId: number): Observable<GroupMember[]> {
    return this.http.get<GroupMember[]>(`${this.baseUrl}/${groupId}/members`);
  }

  // GET /groups/{groupId}/members/details
  getMemberDetails(groupId: number): Observable<GroupMemberDetail[]> {
    return this.http.get<GroupMemberDetail[]>(`${this.baseUrl}/${groupId}/members/details`);
  }

  // GET /groups/{groupId}/members/role/{role}
  getMembersByRole(groupId: number, role: string): Observable<GroupMemberDetail[]> {
    return this.http.get<GroupMemberDetail[]>(`${this.baseUrl}/${groupId}/members/role/${role}`);
  }

  // PATCH /groups/{groupId}/members/{userId}/role
  updateMemberRole(groupId: number, userId: number, dto: GroupMemberRole): Observable<SuccessMessage> {
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${groupId}/members/${userId}/role`, dto);
  }

  // PATCH /groups/{groupId}/transfer-admin
  transferAdmin(groupId: number, currentAdminId: number, newAdminId: number): Observable<SuccessMessage> {
    const params = new HttpParams()
      .set('currentAdminId', currentAdminId.toString())
      .set('newAdminId', newAdminId.toString());
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${groupId}/transfer-admin`, {}, { params });
  }

  // GET /groups/{groupId}/members/search?name=
  searchMembers(groupId: number, name: string): Observable<GroupMemberDetail[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<GroupMemberDetail[]>(`${this.baseUrl}/${groupId}/members/search`, { params });
  }

  // POST /groups/{groupId}/join?userId=
  requestToJoin(groupId: number, userId: number): Observable<SuccessMessage> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.http.post<SuccessMessage>(`${this.baseUrl}/${groupId}/join`, {}, { params });
  }

  // DELETE /groups/{groupId}/leave/{userId}
  leaveGroup(groupId: number, userId: number): Observable<SuccessMessage> {
    return this.http.delete<SuccessMessage>(`${this.baseUrl}/${groupId}/leave/${userId}`);
  }

  // GET /groups/{groupId}/requests
  getPendingRequests(groupId: number): Observable<GroupMember[]> {
    return this.http.get<GroupMember[]>(`${this.baseUrl}/${groupId}/requests`);
  }

  // PATCH /groups/{groupId}/requests/{userId}/approve
  approveRequest(groupId: number, userId: number): Observable<SuccessMessage> {
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${groupId}/requests/${userId}/approve`, {});
  }

  // PATCH /groups/{groupId}/requests/{userId}/reject
  rejectRequest(groupId: number, userId: number): Observable<SuccessMessage> {
    return this.http.patch<SuccessMessage>(`${this.baseUrl}/${groupId}/requests/${userId}/reject`, {});
  }

  // GET /groups/name/{groupName}/members/search?keyword=
  searchMembersByGroupName(groupName: string, keyword: string): Observable<GroupMemberDetail[]> {
    const params = new HttpParams().set('keyword', keyword);
    return this.http.get<GroupMemberDetail[]>(`${this.baseUrl}/name/${groupName}/members/search`, { params });
  }
}

