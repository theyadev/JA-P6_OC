import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import Post from 'src/app/interfaces/post.interface';
import { ThemeService } from 'src/app/services/theme.service';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable, forkJoin, map, of, switchMap } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-posts-feed',
  templateUrl: './feed.component.html',
  styleUrls: [],
})
export class FeedComponent implements OnInit {
  public $posts: Observable<Post[]> = this.postService.feed();
  public $sorting: Observable<'new' | 'old'> = of('new');

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

    this.$sorting.subscribe((sortingCriteria) => {
      this.sortPosts(sortingCriteria);
    });
  }

  formatDate(date: string) {
    const dateObject = new Date(date);

    const day = dateObject.getDate();
    const month = dateObject.getMonth() + 1;
    const year = dateObject.getFullYear();

    return `${day}/${month}/${year}`;
  }

  sortPosts(sortingCriteria: 'new' | 'old') {
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

  changeSorting() {
    this.$sorting.subscribe((sortingCriteria) => {
      const newSorting = sortingCriteria === 'new' ? 'old' : 'new';

      this.$sorting = of(newSorting);
      this.sortPosts(newSorting);
    });
  }
}
