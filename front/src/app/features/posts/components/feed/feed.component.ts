import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import Post from 'src/app/interfaces/post.interface';
import { ThemeService } from 'src/app/services/theme.service';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable, Subscription, forkJoin, map, of, switchMap } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-posts-feed',
  templateUrl: './feed.component.html',
  styleUrls: [],
})
export class FeedComponent implements OnInit {
  public $posts: Observable<Post[]> = this.postService.feed();
  public sorting: 'new' | 'old' = 'new'

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private postService: PostService,
    private themeService: ThemeService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.$posts = this.postService.feed().pipe(
      switchMap((posts) => {
        const postObservables = posts.map((post) => {
          return this.userService.detail(post.userId!.toString()).pipe(
            map((user) => ({
              ...post,
              user: user,
            }))
          );
        });
        return forkJoin(postObservables);
      })
    );

    this.sortPosts('new');
  }

  sortPosts(sortingCriteria: 'new' | 'old'): void {
    this.$posts = this.$posts.pipe(
      map((posts) => {
        return posts.sort((a, b) => {
          const dateA = new Date(a.createdAt!).getTime();
          const dateB = new Date(b.createdAt!).getTime();
          return sortingCriteria === 'new' ? dateB - dateA : dateA - dateB;
        });
      })
    );
  }

  changeSorting(): void {
    const newSorting = this.sorting === 'new' ? 'old' : 'new';

    this.sorting = newSorting;
    this.sortPosts(newSorting);
  }
}
