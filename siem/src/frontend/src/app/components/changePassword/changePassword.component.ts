import {Component, OnInit} from "@angular/core";
import { Router } from '@angular/router';
import { AdminService } from "../../services/admin.service";
import { ChangePasswordDTO } from "../../models";
import {LoggedUtils} from "../../utils/logged.utils";

@Component({
  templateUrl: './changePassword.component.html',
  providers: [AdminService],
})

export class ChangePassword{
  oldPassword: string;
  newPassword1: string;
  newPassword2: string;

  controlMessage: boolean = true;
  //user: User = JSON.parse(sessionStorage.getItem("loginUser"));



  //waiter: Waiter;
  //cook: Cook;
  //bartender: Bartender;
  //guest : Guest;
  //supplier: Supplier;

  constructor(
    private adminService: AdminService,
    private router: Router) { }

  save(): void
  {
    if(this.newPassword1 === this.newPassword2) {
      this.adminService.changePassword(new ChangePasswordDTO(LoggedUtils.getId(),LoggedUtils.getRole(), this.oldPassword, this.newPassword1)).subscribe(
        data => {
          LoggedUtils.clearLocalStorage();
          localStorage.setItem("loggedUser", JSON.stringify(data));
          this.router.navigate(['/allLogs']);
        },
            error => this.badInput()

      );
    }

  }

  isValidForm(): boolean
  {
    let re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,}$/;
    // AsD12#5f <- primer validne lozinke
    if( (!re.test(this.newPassword1) || !re.test(this.newPassword2) ) && this.newPassword1 && this.newPassword2){

      document.getElementById("changePass").innerHTML = "<div class=\"alert alert-danger col-sm-offset-8 col-sm-8\"> Password should be at least 8 characters, contain at least one number, contain at least one upper and lower case letter, contain at least one special character </div>";
      return false;
    }
    if(this.newPassword1 == this.newPassword2 && this.newPassword1 && this.newPassword2 && this.controlMessage) {
      document.getElementById("changePass").innerHTML = "<div class=\"alert alert-success col-sm-offset-4 col-sm-4\"> New passwords match! </div>";
      return true;
    }
    else if((this.newPassword1 || this.newPassword2) && this.controlMessage) {
      document.getElementById("changePass").innerHTML = "<div class=\"alert alert-danger col-sm-offset-4 col-sm-4\"> New passwords do not match! </div>";
      return false;
    }

    else
      return true;
  }

  valuechange(newValue) {
    if(this.newPassword1 != newValue || this.newPassword2 != newValue)
      this.controlMessage = true;
  }

  badInput()
  {
    document.getElementById("changePass").innerHTML = "<div class=\"alert alert-danger col-sm-offset-4 \"> Wrong email/password! </div>";
  }

}

