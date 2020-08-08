import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GreetingService {

  constructor(private http: HttpClient) { }

  getDefault(): any {
    return this.http.get(environment.apiUrl);
  }

  get(id): any {
    return this.http.get(`${environment.apiUrl}/${id}`);
  }

  create(data): any {
    return this.http.post(environment.apiUrl, data);
  }

  update(id, data): any {
    return this.http.put(`${environment.apiUrl}/${id}`, data);
  }

}
