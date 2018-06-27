import construct = Reflect.construct;

export class Log
{
  constructor( public id:number,
               public type:string,
               public description:string,
               public date:Date,
               public ip:string,
               public host:string,
               public facility:string,
               public tag:string
  ) {}
}

export class Link
{
  constructor( public text: string,
               public routerLink: string
  ){}
}

export class SearchByDateDTO
{
  constructor( public date1: Date,
               public date2: Date
  ){}
}

export class Address
{
  constructor( public id:number,
               public city:string,
               public number:string,
               public street:string,
               public postalCode:string,
               public country:string,
  ) {}
}

export class AlarmRule
{
  constructor( public id:string,
               public type:string,
               public description:string,
               public ipAddress:string,
               public date:Date,
               public host:string,
               public facility:string,
               public username:string,
               public repetition:number,
               public minutes:number,
               public typeFlag:boolean,
               public descriptionFlag:boolean,
               public ipAddressFlag:boolean,
               public dateFlag:boolean,
               public hostFlag:boolean,
               public facilityFlag:boolean,
               public usernameFlag:boolean,
               public sameIpAddress:boolean,
               public sameUsername:boolean,
               public sameType:boolean,
               public sameDescription:boolean,
               public sameDate:boolean,
               public sameHost:boolean,
               public sameFacility:boolean
  ) {}
}

export class Alarm
{
  constructor( public id:string,
               public alarmType:AlarmRule,
               public type:string,
               public description:string,
               public ipAdress:string,
               public number:number,
               public dateOfRepetition:Date[],
               public active:boolean,
               public username:string
  ) {}
}


export class AgentData {
  constructor( public id:string,
               public filePaths:string[],
               public types:string[],
               public enabled:boolean,
               public role:string,
               public ports:string[],
               public port:string,
               public name:string,
               public batch:string,
               public level:string
  ) {}
}





export class ChangePasswordDTO
{
  constructor( public userId:number,
               public role:string,
               public oldPassword:string,
               public newPassword:string
  ) {}
}




