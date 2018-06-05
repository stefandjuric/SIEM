import { Injectable } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';

import { Address, ChangePasswordDTO } from "../models";

const httpOptions = {
  headers: new HttpHeaders({ 'content-type': 'application/json' })
};

@Injectable()
export class AdminService
{
  constructor(private http: HttpClient)
  {
  }

  registerUser(username: string, pass: string, role: string, firstname: string, lastname: string, institutionName: string, address: Address, firmName: string, firmDescription: string)
  {
    let registerRequest = {username: username, password: pass, role: role, firstname: firstname, lastname: lastname, institutionName: institutionName, address: address, firmName: firmName, firmDescription: firmDescription};
    let param = JSON.stringify(registerRequest);
    return this.http.post("https://localhost:8443/api/registerUser", param, httpOptions);

  }

  changePassword(dto:ChangePasswordDTO)
  {
    let param = JSON.stringify(dto);
    return this.http.post("https://localhost:8443/user/changePassword", param, httpOptions);
  }

}


