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

  typeEnable:boolean = false;
  descriptionEnable:boolean = false;
  ipAddressEnable:boolean = false;
  sameIpAddress:boolean = false;
  sameUsername:boolean = false;

  type:string;
  description:string;
  ipAddress:string;
  repetition:number;
  minutes:number;


  constructor(private alarmService: AlarmService, private _router: Router) {
  }

  addAlarmRule()
  {
    this.alarmService.addAlarmRule(new AlarmRule(null,this.type, this.description, this.ipAddress, this.repetition,
        this.minutes, this.typeEnable, this.descriptionEnable, this.ipAddressEnable, this.sameIpAddress, this.sameUsername)).subscribe(
      data => console.log(data),
    );
  }
}
