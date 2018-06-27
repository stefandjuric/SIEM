import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent} from './components/home/home.component';
import { SiemComponent } from './components/siem/siem.component';
import { AddUserComponent } from './components/addUser/addUser.component';
import { AlarmComponent } from "./components/alarm/alarm.component";
import { AddAlarmRuleComponent } from "./components/addAlarmRule/addAlarmRule.component";
import { ChangePassword } from "./components/changePassword/changePassword.component";
import { AgentManagerComponent} from "./components/agentManager/agentManager.component";
import { ShowAllAlarmRules } from "./components/showAllAlarmRules/showAllAlarmRules.component";

const appRoutes : Routes =
  [
    {
      path : '',
      component : HomeComponent
    },
    {
      path : 'allLogs',
      component : SiemComponent
    },
    {
      path : 'addUser',
      component : AddUserComponent
    },
    {
      path : 'allAlarms',
      component : AlarmComponent
    },
    {
      path : 'addAlarmComponent',
      component : AddAlarmRuleComponent
    },
    {
      path : 'changePassword',
      component : ChangePassword
    },{
      path : 'agents',
      component : AgentManagerComponent
    },
    {
      path : 'showAllAlarmRule',
      component : ShowAllAlarmRules
    }

  ];

export const routing : ModuleWithProviders = RouterModule.forRoot(appRoutes);

