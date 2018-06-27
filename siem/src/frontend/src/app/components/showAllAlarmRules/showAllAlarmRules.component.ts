import { Component } from '@angular/core';
import { AlarmRule } from "../../models";

import { AlarmService } from "../../services/alarm.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";
import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  moduleId: module.id,
  selector: 'showAllAlarmRules',
  templateUrl: './showAllAlarmRules.component.html',
  styleUrls: ['./showAllAlarmRules.component.css'],
  providers: [AlarmService]

})

export class ShowAllAlarmRules {

  alarmRules:AlarmRule[]=[]

  constructor(private alarmService: AlarmService, private _router: Router) {
    this.alarmService.getAllAlarmRules().subscribe(
      data => {
        this.alarmRules = data;
      }
    );
  }

}
