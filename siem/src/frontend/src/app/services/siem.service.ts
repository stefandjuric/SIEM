import { Injectable } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';

import { Log, SearchByDateDTO } from "../models";

const httpOptions = {
  headers: new HttpHeaders({ 'content-type': 'application/json' })
};

@Injectable()
export class SiemService {

  constructor(private http: HttpClient) {
  }

  getAllLogs()
  {
    return this.http.get<Log[]>("http://localhost:8080/agent/getLogs");
  }

  getLogsByType(type:string)
  {
    return this.http.get<Log[]>("http://localhost:8080/agent/getLogsByType/"+type);
  }

  getLogsByDate(dto:SearchByDateDTO)
  {
    var param = JSON.stringify(dto);
    return this.http.post<Log[]>("http://localhost:8080/agent/getLogsByDate", param, httpOptions);
  }
}
