import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from '../post-card/post-card.component';
import { PostCreateComponent } from '../post-create/post-create.component';
import { Post } from '../../models/post.model';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [FormsModule, CommonModule, PostCardComponent, PostCreateComponent],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.css'
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  loading = true;

  constructor(private postService: PostService) {}

  ngOnInit() {
    this.loadPosts();
  }

  loadPosts() {
    this.postService.getAllPosts().subscribe({
      next: (res: Post[]) => {
        // Sort posts by descending timestamp if necessary
        this.posts = res.sort((a, b) => {
          const dateA = a.timestamp ? new Date(a.timestamp).getTime() : 0;
          const dateB = b.timestamp ? new Date(b.timestamp).getTime() : 0;
          return dateB - dateA;
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}

