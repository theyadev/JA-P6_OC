import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import Post from 'src/app/interfaces/post.interface';
import { ThemeService } from 'src/app/services/theme.service';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable, Subscription, forkJoin, map, of, switchMap } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { SessionService } from 'src/app/services/session.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Comment from 'src/app/interfaces/comment.interface';
import { CommentService } from 'src/app/services/comment.service';
import User from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-posts-form',
  templateUrl: './detail.component.html',
  styleUrls: [],
})
export class DetailComponent implements OnInit, OnDestroy {
  public postId: string;
  public post?: Post;
  public user?: User;
  public theme?: Theme;
  public $comments: Observable<Comment[]> = of([]);

  private $subscription?: Subscription

  public commentForm?: FormGroup;
  constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private postService: PostService,
    private commentService: CommentService,
    private themeService: ThemeService,
    private userService: UserService,
    private router: Router
  ) {
    this.postId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.fetchPost();
    this.initForm()
  }

  ngOnDestroy(): void {
      this.$subscription?.unsubscribe()
  }

  private fetchPost(): void {
    this.$subscription = this.postService
      .detail(this.postId)
      .subscribe((post) => {
        this.post = post;

        this.userService.detail(post.userId!.toString()).subscribe((user) => {
          this.user = user;
        })

        this.themeService.detail(post.themeId!.toString()).subscribe((theme) => {
          this.theme = theme;
        })

        this.fetchComments();
      });
  }

  private fetchComments(): void {
    this.$comments = this.commentService.postComments(this.post?.id!).pipe(
      switchMap((comments) => {
        const commentObservables = comments.map((comment) => {
          return this.userService.detail(comment.userId!.toString()).pipe(
            map((user) => ({
              ...comment,
              user: user,
            }))
          );
        });
        return forkJoin(commentObservables);
      })
    )
  }

  private initForm(): void {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.max(2000)]],
      postId: [this.postId, [Validators.required]],
    });
  }

  public submit(): void {
    const comment = this.commentForm?.value as Comment;

    if (!comment.content) {
      return;
    }
    
    const createdComment = this.commentService.create(comment)
    const subscription = createdComment.subscribe((comment) => {
      this.fetchComments()
      subscription.unsubscribe()
    })
    this.commentForm?.reset()
  }

}
