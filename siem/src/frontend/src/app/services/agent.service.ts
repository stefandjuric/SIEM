import { Injectable } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';

import {AgentData, Log} from "../models";

const httpOptions = {
  headers: new HttpHeaders({ 'content-type': 'application/json' })
};

@Injectable()
export class AgentService {

  constructor(private http: HttpClient) {
  }

  getAllAgents()
  {
    return this.http.get<AgentData[]>("https://localhost:8443/agent/getAgents");
  }

  getAgent(id:string) {
    return this.http.get<AgentData>("https://localhost:8443/agent/getAgent/" + id);
  }

  editAgent(agentData:AgentData)
  {
    let param = JSON.stringify(agentData);
    return this.http.post<AgentData>("https://localhost:8443/agent/addAgentData", param, httpOptions)
  }
}
