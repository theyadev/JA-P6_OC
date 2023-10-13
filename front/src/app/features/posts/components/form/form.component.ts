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
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-posts-form',
  templateUrl: './form.component.html',
  styleUrls: [],
})
export class FormComponent implements OnInit {
  public postForm?: FormGroup;
  public $themes = this.themeService.all();

  public error: string = '';

  constructor(
    private httpClient: HttpClient,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private postService: PostService,
    private themeService: ThemeService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  public submit(): void {
    const post = this.postForm?.value as Post;

    if (!post.themeId) {
      this.error = 'Le thème est manquant.';

      return;
    }

    if (!post.name) {
      this.error = "Le titre de l'article est manquant.";

      return;
    }

    if (!post.content) {
      this.error = "Le contenu de l'article est manquant.";

      return;
    }

    const createdPost = this.postService.create(post);

    createdPost.subscribe(() => {
      this.router.navigate(['/posts']);
    });
  }

  private initForm(): void {
    this.postForm = this.fb.group({
      name: ['', [Validators.required]],
      content: ['', [Validators.required, Validators.max(2000)]],
      themeId: ['', [Validators.required]],
    });
  }
}
