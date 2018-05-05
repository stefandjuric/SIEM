import { Component, NgModule, OnInit } from '@angular/core';
import {LoggedUtils} from "../../utils/logged.utils";
import { AuthenticationService } from "../../services/authentication.service";
import { Link } from "../../models";
import { Router }    from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'navbar',
  templateUrl: 'navbar.component.html'
})
export class NavbarComponent implements OnInit
{

  links: Link[];
  currentRole:string;

  constructor(private authenticationService: AuthenticationService, private _router: Router)
  {
    this.logout();
  }

  ngOnInit() {
    this.authenticationService.getRoleEmitter().subscribe((value:string) => this.changeRole(value));
  }

  private changeRole(role: string)
  {
    this.currentRole = role;
    this.links = [];
    //............

    //........
  }


  addLink(link: Link)
  {
    this.links.push(link);
  }


  logout()
  {
    this.links = [];
    this.addLink({text: "Login", routerLink: "/"});
    this.addLink({text: "All logs", routerLink: "/allLogs"});
    LoggedUtils.clearLocalStorage();
  }
}
