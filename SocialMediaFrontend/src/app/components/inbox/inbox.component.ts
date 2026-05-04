import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from '../../services/message.service';
import { FriendshipService } from '../../services/friendship.service';
import { AuthService } from '../../services/auth.service';
import { Message } from '../../models/message.model';
import { Friendship } from '../../models/friendship.model';

@Component({
  selector: 'app-inbox',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inbox.component.html',
  styleUrl: './inbox.component.css'
})
export class InboxComponent implements OnInit, AfterViewChecked {
  @ViewChild('chatScrollContainer') private chatScrollContainer!: ElementRef;

  currentUserId: number | null = null;
  friends: number[] = []; // List of friend User IDs
  
  activeFriendId: number | null = null;
  conversation: Message[] = [];
  
  newMessageText = '';
  isSending = false;
  loadingFriends = true;
  loadingChat = false;

  constructor(
    private messageService: MessageService,
    private friendshipService: FriendshipService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.loadFriends();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      if (this.chatScrollContainer) {
        this.chatScrollContainer.nativeElement.scrollTop = this.chatScrollContainer.nativeElement.scrollHeight;
      }
    } catch(err) { }
  }

  loadFriends() {
    if (!this.currentUserId) return;
    this.loadingFriends = true;
    
    this.friendshipService.getFriendsByUserId(this.currentUserId).subscribe({
      next: (friendships: Friendship[]) => {
        const friendIds = friendships.map(f => {
          return f.userId1 === this.currentUserId ? f.userId2 : f.userId1;
        });
        this.friends = Array.from(new Set(friendIds));
        this.loadingFriends = false;
      },
      error: (err) => {
        console.error('Failed to load friends', err);
        this.loadingFriends = false;
      }
    });
  }

  selectFriend(friendId: number) {
    this.activeFriendId = friendId;
    this.loadConversation();
  }

  loadConversation() {
    if (!this.currentUserId || !this.activeFriendId) return;
    
    this.loadingChat = true;
    this.messageService.getConversation(this.currentUserId, this.activeFriendId).subscribe({
      next: (messages) => {
        // Sort chronologically (oldest first)
        this.conversation = messages.sort((a, b) => {
          const dateA = a.timestamp ? new Date(a.timestamp).getTime() : 0;
          const dateB = b.timestamp ? new Date(b.timestamp).getTime() : 0;
          return dateA - dateB;
        });
        this.loadingChat = false;
      },
      error: (err) => {
        console.error('Failed to load conversation', err);
        this.loadingChat = false;
      }
    });
  }

  sendMessage() {
    if (!this.currentUserId || !this.activeFriendId || !this.newMessageText.trim()) return;

    this.isSending = true;
    this.messageService.sendMessage({
      senderId: this.currentUserId,
      receiverId: this.activeFriendId,
      messageText: this.newMessageText
    }).subscribe({
      next: () => {
        this.newMessageText = '';
        this.isSending = false;
        this.loadConversation(); // reload chat
      },
      error: (err) => {
        console.error('Failed to send message', err);
        this.isSending = false;
      }
    });
  }
}

