import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable, Subscription, map } from 'rxjs';
import { ThemeService } from 'src/app/services/theme.service';
import User from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: [],
})
export class MeComponent implements OnInit, OnDestroy {
  public $user: Observable<User> = this.userService.me();
  public themes: Theme[] = [];
  public updateForm?: FormGroup;
  private $subscription?: Subscription
  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private router: Router,
    private userService: UserService,
    private themeService: ThemeService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.$user.subscribe((user) => {
      this.initForm(user);
      this.themes = [];
      for (let theme of user.themes) {
        this.themeService.detail(theme.toString()).pipe(
          map((theme) => {
            this.themes.push(theme);
          })
        );
      }
    });
  }

  ngOnDestroy(): void {
      this.$subscription?.unsubscribe()
  }

  unsubscribe(themeId: number) {
    this.themeService.unsubscribe(themeId.toString()).pipe(
      map((response) => {
        this.themes = this.themes.filter((theme) => theme.id !== themeId);
      })
    );
  }

  private initForm(user: User): void {
    this.updateForm = this.fb.group({
      username: [user.username, [Validators.required]],
      email: [user.email, [Validators.required, Validators.email]],
    });
  }

  logout(): void {
    console.log('logout');
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  submit(): void {
    const user = this.updateForm?.value as User;

    this.$subscription = this.userService.update(user).subscribe((user) => {
      this.initForm(user);
    });
  }
}
