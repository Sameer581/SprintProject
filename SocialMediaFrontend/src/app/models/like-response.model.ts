export interface LikeResponse {
  likeId: number;
  userId: number;
  postId: number;
  timestamp: string;
  message: string;
  totalLikes: number;
}

