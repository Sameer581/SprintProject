import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';
import { Notification } from '../../models/notification.model';

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
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUserId();
    this.loadNotifications();
  }

  loadNotifications() {
    if (!this.userId) return;
    this.loading = true;
    this.notificationService.getNotificationsByUser(this.userId).subscribe({
      next: (res) => {
        // Sort newest first
        this.notifications = res.sort((a, b) => {
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
    if (!notificationId) return;
    
    this.notificationService.deleteNotification(notificationId).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.notificationId !== notificationId);
      },
      error: (err) => console.error('Failed to delete notification', err)
    });
  }
}

