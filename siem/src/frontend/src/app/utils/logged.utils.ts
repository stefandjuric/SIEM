export class LoggedUtils
{
  static getId()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).id;
  }

  static getToken()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).token;
  }

  static getIsResponsible()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).isResponsible;
  }

  static hasRole(role: string)
  {
    let roles = JSON.parse(localStorage.getItem("loggedUser")).role;
    return roles.indexOf(role) != -1;
  }

  static getRole()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).role;
  }

  static getUsername()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).username;
  }

  static getUser()
  {
    return JSON.parse(localStorage.getItem("loggedUser"));
  }

  static clearLocalStorage()
  {
    localStorage.clear();
  }

  static isEmpty()
  {
    return localStorage.getItem("loggedUser") === null;
  }

  static isLogged()
  {

  }
}
