export interface Like {
  likeId?: number;
  userId: number;
  postId: number;
  timestamp?: string;
  message?: string;
  totalLikes?: number;
}

