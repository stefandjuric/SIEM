import { Component } from '@angular/core';
import { AuthenticationService } from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']

})
export class HomeComponent
{
  private usr: string;
  private password: string;

  constructor(private authenticationService: AuthenticationService,private router:Router)
  {
    this.usr = "";
    this.password = "";
  }

  authenticate()
  {
    this.authenticationService.authenticateUser(this.usr, this.password).subscribe(
      data => localStorage.setItem("loggedUser", JSON.stringify(data)),
      error => this.badInput(),
      () => {
        this.callEmitter();
      }
    );

  }

  callEmitter()
  {
    this.authenticationService.emitRole(this.usr);

    setTimeout(() => {
        this.router.navigate(['/allLogs']);
      },
      1000);
  }

  badInput()
  {
    document.getElementById("login").innerHTML = "<div class=\"alert alert-danger col-sm-offset-4 \"> Wrong email/password! </div>";
  }

}
