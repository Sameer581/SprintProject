import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';
import { FriendshipService } from '../../services/friendship.service';
import { MessageService } from '../../services/message.service';
import { Notification } from '../../models/notification.model';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-notification-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-list.component.html',
  styleUrl: './notification-list.component.css'
})
export class NotificationListComponent implements OnInit {
  userId: number | null = null;
  notifications: Notification[] = [];
  loading = true;

  constructor(
    private notificationService: NotificationService,
    private authService: AuthService,
    private friendshipService: FriendshipService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUserId();
    this.loadNotifications();
  }

  loadNotifications() {
    if (!this.userId) return;
    this.loading = true;
    
    forkJoin({
      dbNotifs: this.notificationService.getNotificationsByUser(this.userId).pipe(catchError(() => of([]))),
      friendReqs: this.friendshipService.getPendingRequests(this.userId).pipe(catchError(() => of([]))),
      messages: this.messageService.getMessagesByReceiver(this.userId).pipe(catchError(() => of([])))
    }).subscribe({
      next: (results) => {
        let combined: Notification[] = [...results.dbNotifs];

        // Add pending friend requests
        results.friendReqs.forEach(req => {
          combined.push({
            userId: this.userId!,
            content: `You have a pending friend request from ${req.username1 || 'User ' + req.userId1}`,
            timestamp: new Date().toISOString() // Pending requests typically don't show age
          });
        });

        // Add recent DMs (group by sender to avoid spam)
        const recentSenders = new Set<number>();
        // Sort messages newest first to get the most recent per sender
        const sortedMsgs = results.messages.sort((a, b) => {
           return new Date(b.timestamp || 0).getTime() - new Date(a.timestamp || 0).getTime();
        });
        
        sortedMsgs.forEach(msg => {
          if (!recentSenders.has(msg.senderId)) {
            combined.push({
              userId: this.userId!,
              content: `You received a new direct message from User ${msg.senderId}`,
              timestamp: msg.timestamp || new Date().toISOString()
            });
            recentSenders.add(msg.senderId);
          }
        });

        // Sort all newest first
        this.notifications = combined.sort((a, b) => {
          const dateA = a.timestamp ? new Date(a.timestamp).getTime() : 0;
          const dateB = b.timestamp ? new Date(b.timestamp).getTime() : 0;
          return dateB - dateA;
        });
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load notifications', err);
        this.loading = false;
      }
    });
  }

  markAsRead(notification: Notification) {
    // Backend doesn't support 'isRead', so we don't do anything, 
    // or we could just delete it to simulate 'dismissing' it.
  }

  deleteNotification(notificationId: number | undefined) {
    if (!notificationId) return; // Cannot delete virtual notifications
    
    this.notificationService.deleteNotification(notificationId).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.notificationId !== notificationId);
      },
      error: (err) => console.error('Failed to delete notification', err)
    });
  }
}

