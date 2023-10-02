import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: [],
})
export class HomeComponent implements OnInit {
  $isLogged = this.sessionService.$isLogged();
  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  ngOnInit(): void {}
}
