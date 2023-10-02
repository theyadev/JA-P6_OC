import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: [],
})
export class NavbarComponent implements OnInit {
  @Input() isLogged?: boolean;
  // @Input() value?: string | number;
  constructor() {}

  ngOnInit(): void {}
}
