import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import Theme from 'src/app/interfaces/theme.interface';
import { Observable } from 'rxjs';
import { ThemeService } from 'src/app/services/theme.service';
import { UserService } from 'src/app/services/user.service';
import User from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: [],
})
export class ThemesComponent implements OnInit {
  public $themes: Observable<Theme[]> = this.themeService.all();
  public $user: Observable<User> = this.userService.me();

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private router: Router,
    private themeService: ThemeService,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  subscribe(themeId: number) {
    this.themeService.subscribe(themeId.toString()).subscribe((response) => {
      this.$user = this.userService.me();
    });
  }

  unsubscribe(themeId: number) {
    this.themeService.unsubscribe(themeId.toString()).subscribe((response) => {
      this.$user = this.userService.me();
    });
  }
}
