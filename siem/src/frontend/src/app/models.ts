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
               public ipAdress:string,
               public repetition:number,
               public minutes:number,
               public typeFlag:boolean,
               public descriptionFlag:boolean,
               public ipAddressFlag:boolean,
               public sameIpAddress:boolean,
               public sameUsername:boolean
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




