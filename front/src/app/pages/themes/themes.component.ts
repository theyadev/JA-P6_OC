import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: [],
})
export class ThemesComponent implements OnInit {
  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {

  }
}
