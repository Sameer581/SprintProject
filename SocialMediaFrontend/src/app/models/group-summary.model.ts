export interface GroupSummary {
  groupId: number;
  groupName: string;
  adminId: number;
  adminName: string;
  totalMembers: number;
  roleCounts: { [role: string]: number };
}

