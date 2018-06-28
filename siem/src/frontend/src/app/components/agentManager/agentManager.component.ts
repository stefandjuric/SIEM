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
  public addType:boolean = false;
  public newFilePath:string = "";
  public selectedEnabled:string = "";
  public selectedFilePath:string= "";
  public selectedType:string="";
  public typesVisible = false;
  public selectedTypeValue = "INFO";

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
        this.selectedEnabled = this.agent.enabled.toString();
        if (this.agent.types.length != 0){
          this.selectedType = this.agent.types[0];
        }
        if (this.agent.filePaths.length != 0) {
          this.selectedFilePath = this.agent.filePaths[0]
        }
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
    if (this.agent.filePaths.length != 0){
      this.selectedFilePath = this.agent.filePaths[0];
    }
  }

  public deleteFilePath(){
    for (var i = 0 ; i < this.agent.filePaths.length; i++) {
      if (this.selectedFilePath.includes(this.agent.filePaths[i])) {
        this.agent.filePaths.splice(i,1);
      }
    }
    if (this.agent.filePaths.length != 0){
      this.selectedFilePath = this.agent.filePaths[0];
    }
  }


  public addNewType(){
    this.typesVisible = true;
  }


  public cancelType(){
    this.typesVisible = false;
  }


  public confirmNewType(){
    if (!this.agent.types.includes(this.selectedTypeValue)) {
        this.agent.types.push(this.selectedTypeValue);
      }
    this.typesVisible = false;
  }


  public deleteType(){
    for (var i = 0 ; i < this.agent.types.length; i++) {
      if (this.selectedType.includes(this.agent.types[i])) {
        this.agent.types.splice(i,1);
      }
    }
    if (this.agent.types.length != 0){
      this.selectedType = this.agent.types[0];
    }
  }

  public add()
  {
    let flag = false;
    if(this.selectedEnabled == "true") flag = true;
    this.agent.enabled = flag;
    this.agentService.editAgent(this.agent).subscribe(
      data => {
        console.log(data);
      });
  }

}

