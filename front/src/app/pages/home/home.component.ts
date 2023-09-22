import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }

  test() {
    this.httpClient.post<any>("/api/comment", {}).subscribe((x) => {
      console.log(x)
    })
  }
}
