import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { PartyDetailComponent } from 'app/entities/party/party-detail.component';
import { Party } from 'app/shared/model/party.model';

describe('Component Tests', () => {
  describe('Party Management Detail Component', () => {
    let comp: PartyDetailComponent;
    let fixture: ComponentFixture<PartyDetailComponent>;
    const route = ({ data: of({ party: new Party(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [PartyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PartyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load party on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.party).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
