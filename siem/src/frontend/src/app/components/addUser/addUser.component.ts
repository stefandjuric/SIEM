import { Component } from '@angular/core';
import { AdminService } from "../../services/admin.service";
import { Address } from "../../models";

@Component({
  moduleId: module.id,
  selector: 'addUser',
  styleUrls: ['./addUser.component.css'],
  templateUrl: './addUser.component.html',
  providers: [AdminService]
})

export class AddUserComponent  {
  private usr: string;
  private password: string;
  private userType: string;
  private firstname: string;
  private lastname: string;
  private institutionName: string;
  private city:string;
  private buildingNumber:string;
  private buildingStreet:string;
  private postalCode:string;
  private country:string;
  private firmName:string;
  private firmDescription:string;

  constructor(private adminService: AdminService)
  {
    this.usr = "";
    this.password = "";
    this.userType = "";
    this.firstname = "";
    this.lastname = "";
    this.institutionName = "";
    this.city = "";
    this.buildingNumber = "";
    this.buildingStreet = "";
    this.postalCode = "";
    this.country = "";
    this.firmName = "";
    this.firmDescription = "";
  }

  register()
  {
    this.adminService.registerUser(this.usr, this.password, this.userType, this.firstname, this.lastname, this.institutionName,
      new Address(null,this.city, this.buildingNumber, this.buildingStreet, this.postalCode, this.country), this.firmName, this.firmDescription).subscribe(
      data => console.log(data),
    );
  }
}
