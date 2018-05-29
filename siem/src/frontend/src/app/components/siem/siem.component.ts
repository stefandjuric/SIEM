import { Component } from '@angular/core';
import { Log, SearchByDateDTO } from "../../models";

import { SiemService } from "../../services/siem.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";
import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  moduleId: module.id,
  selector: 'siem',
  templateUrl: './siem.component.html',
  styleUrls: ['./siem.component.css'],
  providers: [SiemService]

})

export class SiemComponent
{

  logs:Log[];
  typeSearch:string="";
  regexString:string="";
  date1:Date=null;
  date2:Date=null;
  searchedLogs:Log[]=[];


  postsSubscription:Subscription;
  timerSubscription:Subscription;


  loadNewData:boolean=true;

  constructor(private siemService: SiemService, private _router: Router)
  {
    this.refreshData();
  }

  private refreshData(): void {
    if(this.loadNewData) {
      this.postsSubscription = this.siemService.getAllLogs().subscribe(
        data => {
          this.logs = data;
          this.subscribeToData();
        });
    }
  }

  private subscribeToData(): void {
      this.timerSubscription=Observable.timer(5000).first().subscribe(() => this.refreshData());
  }

  searchByType()
  {
    if(this.typeSearch == "")
    {
      this.loadNewData = true;
      this.refreshData();
    }
    else{
      this.loadNewData = false;
      this.siemService.getLogsByType(this.typeSearch).subscribe
      (
        data => this.logs = data
      );
    }
  }

  searchByData()
  {
    this.loadNewData = false;
    this.siemService.getLogsByDate(new SearchByDateDTO(this.date1, this.date2)).subscribe
    (
      data => this.logs = data
    );
  }

  refresh()
  {
    this.loadNewData = true;
    this.typeSearch="";
    this.regexString="";
    this.refreshData();
  }

  searchByRegex()
  {
    if(this.regexString == "")
    {
      this.loadNewData = true;
      this.refreshData();
    }
    else{
      this.loadNewData = false;
      this.siemService.getLogsByRegex(this.regexString).subscribe
      (
        data => this.logs = data
      );
    }
  }
}
