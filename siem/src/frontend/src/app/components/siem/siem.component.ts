import { Component } from '@angular/core';
import { Log, SearchByDateDTO } from "../../models";

import { SiemService } from "../../services/siem.service";
import { Router } from '@angular/router';
import {Observable, Subscription} from "rxjs";

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
  date1:Date=null;
  date2:Date=null;
  searchedLogs:Log[]=[];


  postsSubscription:Subscription;
  timerSubscription:Subscription;

  constructor(private siemService: SiemService, private _router: Router)
  {
    this.refreshData();
  }

  private refreshData(): void {
    console.log("aaaa        " +this.searchedLogs);
    this.postsSubscription=this.siemService.getAllLogs().subscribe(
      data => {
        this.logs = data;
        this.subscribeToData();
      });
  }

  private subscribeToData(): void {
    this.timerSubscription=Observable.timer(5000).first().subscribe(() => this.refreshData());
  }

  searchByType()
  {
    this.siemService.getLogsByType(this.typeSearch).subscribe
    (
      data => this.searchedLogs = data,
      () => {console.log(this.searchedLogs);}
    );
  }

  searchByData()
  {
    this.siemService.getLogsByDate(new SearchByDateDTO(this.date1, this.date2)).subscribe
    (
      data => this.searchedLogs = data
    );
  }
}
