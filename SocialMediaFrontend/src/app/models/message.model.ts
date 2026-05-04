export interface Message {
  messageId?: number;
  senderId: number;
  receiverId: number;
  messageText: string;
  timestamp?: string;
}

