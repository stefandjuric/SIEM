import { Injectable } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';

import { AlarmRule, Alarm } from "../models";

const httpOptions = {
  headers: new HttpHeaders({ 'content-type': 'application/json' })
};

@Injectable()
export class AlarmService {

  constructor(private http: HttpClient) {
  }

  getAllAlarms()
  {
    return this.http.get<Alarm[]>("https://localhost:8443/alarm/getAlarms");
  }

  addAlarmRule(alarmRule:AlarmRule)
  {
    let param = JSON.stringify(alarmRule);
    return this.http.post<AlarmRule>("https://localhost:8443/alarm/addAlarmRule", param, httpOptions);
  }

  getAllAlarmRules()
  {
    return this.http.get<AlarmRule[]>("https://localhost:8443/alarm/getAllAlarmRules");
  }
}
