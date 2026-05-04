import { Routes } from '@angular/router';

import { authGuard } from './auth.guard';

import { FeedComponent } from './components/feed/feed.component';
import { ProfileComponent } from './components/profile/profile.component';
import { FriendListComponent } from './components/friend-list/friend-list.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';
import { GroupListComponent } from './components/group-list/group-list.component';
import { GroupDetailComponent } from './components/group-detail/group-detail.component';
import { AuthComponent } from './components/auth/auth.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SettingsComponent } from './components/settings/settings.component';

export const routes: Routes = [

  { path: '', redirectTo: 'auth', pathMatch: 'full' },
  { path: 'auth', component: AuthComponent },

  {
    path: '',
    component: DashboardComponent,
    canActivate: [authGuard],
    children: [

      { path: 'feed', component: FeedComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'profile/:id', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'friends', component: FriendListComponent },
      { path: 'messages', component: InboxComponent },
      { path: 'notifications', component: NotificationListComponent },
      { path: 'groups', component: GroupListComponent },
      { path: 'groups/:id', component: GroupDetailComponent },

      // optional default after login
      { path: '', redirectTo: 'feed', pathMatch: 'full' }

    ]
  },
  { path: '**', redirectTo: 'auth' }
];