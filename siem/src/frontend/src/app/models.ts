import construct = Reflect.construct;

export class Log
{
  constructor( public id:number,
               public type:string,
               public description:string,
               public date:Date,
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


