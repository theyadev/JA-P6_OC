import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import Post from 'src/app/interfaces/post.interface';
import { ThemeService } from 'src/app/services/theme.service';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable, forkJoin, map, of, switchMap } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: [],
})
export class PostsComponent implements OnInit {
  public $posts: Observable<Post[]> = this.postService.feed();
  public $sorting: Observable<"new" | "old"> = of("new")

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
      switchMap(posts => {
        const postObservables = posts.map(post => {
          return this.userService.detail(post.userId!.toString()).pipe(
            map(user => ({
              ...post,
              user: user,
            }))
          );
        });
        return forkJoin(postObservables);
      })
    );

    // onchange this.$sorting, sort the posts
    this.$sorting.subscribe(sortingCriteria => {
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

  sortPosts(sortingCriteria: "new" | "old") {
    console.log(sortingCriteria)
    this.$posts.subscribe(posts => {
      if (sortingCriteria === "new") {
        // Sort the posts by a new-to-old criteria
        posts.sort((a, b) => {
          return new Date(b.createdAt!).getTime() - new Date(a.createdAt!).getTime();
        });
      } else if (sortingCriteria === "old") {
        // Sort the posts by an old-to-new criteria
        posts.sort((a, b) => {
          return new Date(a.createdAt!).getTime() - new Date(b.createdAt!).getTime();
        });
      }
    });
  }

  changeSorting() {
    this.$sorting.subscribe(sortingCriteria => {
      if (sortingCriteria === "new") {
        this.$sorting = of("old");
      } else if (sortingCriteria === "old") {
        this.$sorting = of("new");
      }
    });
  }
}
