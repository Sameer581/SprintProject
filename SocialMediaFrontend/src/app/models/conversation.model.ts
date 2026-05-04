// Frontend-only model: backend derives conversations from messages grouped by user pair
export interface Conversation {
  otherUserId: number;
  otherUsername: string;
  lastMessage: string;
  lastTimestamp: string;
}

