import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: [],
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Input() isLogged?: boolean;

  $router?: Subscription
  currentRoute = this.router.url;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.$router = this.router.events.subscribe((event) => {
      this.currentRoute = this.router.url;
    });
  }

  ngOnDestroy(): void {
      this.$router?.unsubscribe()
  }
}
