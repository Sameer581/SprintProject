export interface Friendship {
  friendshipId?: number;
  userId1: number;
  username1?: string;
  userId2: number;
  username2?: string;
  status?: 'pending' | 'accepted';
}

