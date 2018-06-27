import { Component } from '@angular/core';
import { AgentData } from "../../models";

import {AgentService} from "../../services/agent.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";
import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  moduleId: module.id,
  selector: 'agent',
  templateUrl: './agentManager.component.html',
  styleUrls: ['./agentManager.component.css'],
  providers: [AgentService]

})

export class AgentManagerComponent
{

  postsSubscription:Subscription;
  timerSubscription:Subscription;

  public agents:AgentData[];
  public levels: String[] = [];
  public selectedLevel: String;
  public agentsVisible: boolean = true;
  public agent:AgentData = null;
  public addFilePath:boolean = false;
  public  addType:boolean = false;
  public  newFilePath:string = "";

  constructor(private agentService: AgentService, private _router: Router)
  {
    this.refreshData();
    this.selectedLevel = "1";
  }

  private refreshData(): void {
    this.postsSubscription = this.agentService.getAllAgents().subscribe(
      data => {
        this.agents = data;
        for (var item of this.agents) {
          if (!this.levels.includes(item.level)) {
            this.levels.push(item.level);
          }
        }
      });

  }


  private editAgent(id:string){
    this.agentService.getAgent(id).subscribe(
      data => {
        this.agent = data;
        this.agentsVisible = false;
      });
  }


  public addNewFilePath(){
     this.addFilePath = true;
  }

  public cancel(){
    this.newFilePath = "";
    this.addFilePath = false;
  }

  public confirm(){
    this.agent.filePaths.push(this.newFilePath);
    this.newFilePath = "";
    this.addFilePath = false;
  }
}

