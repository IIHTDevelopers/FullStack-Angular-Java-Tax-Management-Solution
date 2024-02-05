import { Component, OnInit } from '@angular/core';
import { TaxService } from '../../services/tax.service';
import { Tax } from '../../models/tax-management.model';

@Component({
  selector: 'app-tax-management',
  templateUrl: './tax-management.component.html',
  styleUrls: ['./tax-management.component.css']
})
export class TaxManagementComponent implements OnInit {
  taxes: Tax[] = [];
  selectedTax: Tax = {
    taxFormId: 0,
    formType: '',
    filingDate: new Date(),
    totalTaxAmount: 0,
    userId: 0
  };

  constructor(private taxService: TaxService) { }

  ngOnInit(): void {
    this.loadTaxes();
  }

  loadTaxes(): void {
    this.taxService.getAllTaxes().subscribe(
      taxes => {
        this.taxes = taxes;
      },
      error => {
        console.error('Error loading taxes:', error);
      }
    );
  }

  addTax(): void {
    this.taxService.createTax(this.selectedTax).subscribe(
      () => {
        this.loadTaxes();
        this.selectedTax = this.createEmptyTax(); // Clear the form
      },
      error => {
        console.error('Error adding tax:', error);
      }
    );
  }

  showUpdateForm(id: number): void {
    // Retrieve tax details and display them in the form
    const selectedTax = this.taxes.find(tax => tax.taxFormId === id);
    if (selectedTax) {
      this.selectedTax = { ...selectedTax };
    }
  }

  updateTaxApi(): void {
    this.taxService.updateTax(this.selectedTax.taxFormId, this.selectedTax).subscribe(
      () => {
        this.loadTaxes();
        this.selectedTax = this.createEmptyTax(); // Clear the form
      },
      error => {
        console.error('Error updating tax:', error);
      }
    );
  }

  deleteTax(id: number): void {
    this.taxService.deleteTax(id).subscribe(
      () => {
        this.loadTaxes();
        this.selectedTax = this.createEmptyTax(); // Clear the form
      },
      error => {
        console.error('Error deleting tax:', error);
      }
    );
  }

  selectTax(tax: Tax): void {
    this.selectedTax = { ...tax };
  }

  createEmptyTax(): Tax {
    return {
      taxFormId: 0,
      formType: '',
      filingDate: new Date(),
      totalTaxAmount: 0,
      userId: 0
    };
  }
}
