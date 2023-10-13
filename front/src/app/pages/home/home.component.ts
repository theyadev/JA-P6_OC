import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: [],
})
export class HomeComponent implements OnInit {
  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {}
}
