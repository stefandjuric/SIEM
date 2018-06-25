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
    return this.http.get<Log[]>("https://localhost:8443/agent/getLogs");
  }

  getLogsByType(type:string)
  {
    return this.http.get<Log[]>("https://localhost:8443/agent/getLogsByType/"+type);
  }

  getLogsByDate(dto:SearchByDateDTO)
  {
    var param = JSON.stringify(dto);
    return this.http.post<Log[]>("https://localhost:8443/agent/getLogsByDate", param, httpOptions);
  }

  getLogsByRegex(regex:string)
  {
    var param = JSON.stringify(regex);
    return this.http.post<Log[]>("https://localhost:8443/agent/getLogsByRegex",param, httpOptions);
  }
}
