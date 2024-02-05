import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tax } from '../models/tax-management.model';

@Injectable({
  providedIn: 'root'
})
export class TaxService {
  private apiUrl = 'http://127.0.0.1:8081/taxmanagement/api/taxes';

  constructor(private http: HttpClient) { }

  getAllTaxes(): Observable<Tax[]> {
    return this.http.get<Tax[]>(`${this.apiUrl}`);
  }

  getTaxById(id: number): Observable<Tax> {
    return this.http.get<Tax>(`${this.apiUrl}/${id}`);
  }

  createTax(tax: Tax): Observable<Tax> {
    return this.http.post<Tax>(`${this.apiUrl}`, tax);
  }

  updateTax(id: number, tax: Tax): Observable<Tax> {
    return this.http.put<Tax>(`${this.apiUrl}/${id}`, tax);
  }

  deleteTax(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
