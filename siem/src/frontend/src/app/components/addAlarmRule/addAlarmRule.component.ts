import { Component } from '@angular/core';
import { AlarmRule } from "../../models";

import { AlarmService } from "../../services/alarm.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";
import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  moduleId: module.id,
  selector: 'alarm',
  templateUrl: './addAlarmRule.component.html',
  styleUrls: ['./addAlarmRule.component.css'],
  providers: [AlarmService]

})

export class AddAlarmRuleComponent {

  typeEnable: boolean = false;
  descriptionEnable: boolean = false;
  ipAddressEnable: boolean = false;
  dateEnable: boolean = false;
  usernameEnable: boolean = false;
  hostEnable: boolean = false;
  facilityEnable: boolean = false;


  sameIpAddress: boolean = false;
  sameUsername: boolean = false;
  sameType: boolean = false;
  sameDescription: boolean = false;
  sameDate: boolean = false;
  sameHost: boolean = false;
  sameFacility: boolean = false;


  type: string;
  description: string;
  ipAddress: string;
  username: string;
  host: string;
  facility: string;
  date: Date;


  repetition: number;
  minutes: number;


  constructor(private alarmService: AlarmService, private _router: Router) {
  }

  addAlarmRule() {
    if(this.typeEnable) this.sameType = false;
    if(this.descriptionEnable) this.sameDescription = false;
    if(this.ipAddressEnable) this.sameIpAddress = false;
    if(this.dateEnable) this.sameDate = false;
    if(this.usernameEnable) this.sameUsername = false;
    if(this.hostEnable) this.sameHost = false;
    if(this.facilityEnable) this.sameFacility = false;
    this.alarmService.addAlarmRule(new AlarmRule(null, this.type, this.description, this.ipAddress, null, this.host, this.facility,
      this.username, this.repetition, this.minutes, this.typeEnable, this.descriptionEnable, this.ipAddressEnable, false, this.hostEnable,
      this.facilityEnable, this.usernameEnable, this.sameIpAddress, this.sameUsername, this.sameType,
      this.sameDescription, false, this.sameHost, this.sameFacility)).subscribe(
      data => {
        console.log(data);
        this._router.navigate(['/showAllAlarmRule']);
      }
    );
  }
}
