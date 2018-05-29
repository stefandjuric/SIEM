import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent} from './components/home/home.component';
import { SiemComponent } from './components/siem/siem.component';
import { AddUserComponent } from './components/addUser/addUser.component';

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

  ];

export const routing : ModuleWithProviders = RouterModule.forRoot(appRoutes);

