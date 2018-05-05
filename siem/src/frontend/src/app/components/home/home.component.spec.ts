import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpModule } from '@angular/http';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';


import { HomeComponent } from './home.component';
import { AuthenticationService } from "../../services/authentication.service";

describe('HomeComponent', () => {
  let componentForHome: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let authenticationService: any;
  let router: any;

  beforeEach(() => {
    let authenticationServiceMock = {
      authenticateUser: jasmine.createSpy('authenticateUser')
        .and.returnValue(Promise.resolve()),

      emitRole: jasmine.createSpy('emitRole')
        .and.returnValue(Promise.resolve()),
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      imports: [
        HttpModule,
        HttpClientModule,
        FormsModule
      ],
      declarations: [ HomeComponent ],
      providers:    [
        {provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(HomeComponent);
    componentForHome = fixture.componentInstance;
    authenticationService = TestBed.get(AuthenticationService);
    router = TestBed.get(Router);
  });

  it('should authenticate user', () => {
    fixture.whenStable()
      .then(() => {
        componentForHome.authenticate();
        expect(authenticationService.authenticateUser).toHaveBeenCalled();
      });
  });

  it('should call emitter', () => {
    fixture.whenStable()
      .then(() => {
        componentForHome.authenticate();
        expect(authenticationService.emitRole).toHaveBeenCalled();
      });
  });

});
