import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import {FormsModule} from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './auth/token.interceptor';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';

import { NavbarComponent } from './components/navbar/navbar.component';
import { AppComponent }  from './app.component';
import {HomeComponent} from "./components/home/home.component";
import { AddUserComponent } from "./components/addUser/addUser.component";
import { AlarmComponent } from "./components/alarm/alarm.component";
import { AddAlarmRuleComponent } from "./components/addAlarmRule/addAlarmRule.component";
import { ChangePassword } from "./components/changePassword/changePassword.component";
import { AgentManagerComponent} from "./components/agentManager/agentManager.component";
import { ShowAllAlarmRules } from "./components/showAllAlarmRules/showAllAlarmRules.component";


import {SiemComponent} from "./components/siem/siem.component";


import { AuthenticationService } from "./services/authentication.service";

import { routing } from "./app.routes";





@NgModule({
  imports:      [
    BrowserModule,
    routing,
    HttpModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    ReactiveFormsModule,
    NgxPaginationModule

  ],
  declarations: [
    AppComponent,
    NavbarComponent,

    HomeComponent,

    SiemComponent,
    AddUserComponent,
    AlarmComponent,
    AddAlarmRuleComponent,
    ChangePassword,
    AgentManagerComponent,
    ChangePassword,
    ShowAllAlarmRules
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthenticationService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
