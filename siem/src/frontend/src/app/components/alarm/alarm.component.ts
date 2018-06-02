import { Component } from '@angular/core';
import { Alarm } from "../../models";

import { AlarmService } from "../../services/alarm.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";
import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  moduleId: module.id,
  selector: 'alarm',
  templateUrl: './alarm.component.html',
  styleUrls: ['./alarm.component.css'],
  providers: [AlarmService]

})

export class AlarmComponent
{

  postsSubscription:Subscription;
  timerSubscription:Subscription;

  alarms:Alarm[];

  constructor(private alarmService: AlarmService, private _router: Router)
  {
    this.refreshData();
  }

  private refreshData(): void {
    this.postsSubscription = this.alarmService.getAllAlarms().subscribe(
      data => {
        this.alarms = data;
        this.subscribeToData();
      });

  }

  private subscribeToData(): void {
    this.timerSubscription=Observable.timer(5000).first().subscribe(() => this.refreshData());
  }


}

