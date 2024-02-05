import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TaxService } from '../../services/tax.service';
import { Tax } from '../../models/tax-management.model';
import { TaxManagementComponent } from './tax-management.component';

describe('TaxService', () => {
  let component: TaxManagementComponent;
  let service: TaxService;
  let httpTestingController: HttpTestingController;
  let fixture: ComponentFixture<TaxManagementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaxManagementComponent],
      imports: [HttpClientTestingModule],
      providers: [TaxService]
    });
    fixture = TestBed.createComponent(TaxManagementComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(TaxService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('boundary', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });

    it('should fetch all taxes', () => {
      const expectedTaxes: Tax[] = [
        {
          taxFormId: 1,
          formType: '',
          filingDate: new Date(),
          totalTaxAmount: 0,
          userId: 0
        }
      ];
      service.getAllTaxes().subscribe(taxes => {
        expect(taxes).toEqual(expectedTaxes);
      });
      const req = httpTestingController.expectOne('http://127.0.0.1:8081/taxmanagement/api/taxes');
      expect(req.request.method).toEqual('GET');
      req.flush(expectedTaxes);
    });

    it('should get tax by ID', () => {
      const testTax: Tax = {
        taxFormId: 1,
        formType: '',
        filingDate: new Date(),
        totalTaxAmount: 0,
        userId: 0
      };
      service.getTaxById(1).subscribe(tax => {
        expect(tax).toEqual(testTax);
      });
      const req = httpTestingController.expectOne('http://127.0.0.1:8081/taxmanagement/api/taxes/1');
      expect(req.request.method).toEqual('GET');
      req.flush(testTax);
    });

    it('should create a new tax', () => {
      const newTax: Tax = {
        taxFormId: 1,
        formType: '',
        filingDate: new Date(),
        totalTaxAmount: 0,
        userId: 0
      };
      service.createTax(newTax).subscribe(tax => {
        expect(tax).toEqual(newTax);
      });
      const req = httpTestingController.expectOne('http://127.0.0.1:8081/taxmanagement/api/taxes');
      expect(req.request.method).toEqual('POST');
      req.flush(newTax);
    });

    it('should update an existing tax', () => {
      const updatedTax: Tax = {
        taxFormId: 1,
        formType: '',
        filingDate: new Date(),
        totalTaxAmount: 0,
        userId: 0
      };
      service.updateTax(updatedTax.taxFormId, updatedTax).subscribe(tax => {
        expect(tax).toEqual(updatedTax);
      });
      const req = httpTestingController.expectOne(`http://127.0.0.1:8081/taxmanagement/api/taxes/${updatedTax.taxFormId}`);
      expect(req.request.method).toEqual('PUT');
      req.flush(updatedTax);
    });

    it('should delete a tax', () => {
      const taxFormId = 1;
      service.deleteTax(taxFormId).subscribe(response => {
        expect(response).toBeUndefined();
      });
      const req = httpTestingController.expectOne(`http://127.0.0.1:8081/taxmanagement/api/taxes/${taxFormId}`);
      expect(req.request.method).toEqual('DELETE');
      req.flush({});
    });
  });
});
