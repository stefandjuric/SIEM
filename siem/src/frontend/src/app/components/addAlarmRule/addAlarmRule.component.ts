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
    this.alarmService.addAlarmRule(new AlarmRule(null, this.type, this.description, this.ipAddress, this.date, this.host, this.facility,
      this.username, this.repetition, this.minutes, this.typeEnable, this.descriptionEnable, this.ipAddressEnable, this.dateEnable, this.hostEnable,
      this.facilityEnable, this.usernameEnable, this.sameIpAddress, this.sameUsername, this.sameType,
      this.sameDescription, this.sameDate, this.sameHost, this.sameFacility)).subscribe(
      data => console.log(data),
    );
  }
}
