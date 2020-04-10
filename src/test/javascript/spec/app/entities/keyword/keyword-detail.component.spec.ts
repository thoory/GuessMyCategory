import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { KeywordDetailComponent } from 'app/entities/keyword/keyword-detail.component';
import { Keyword } from 'app/shared/model/keyword.model';

describe('Component Tests', () => {
  describe('Keyword Management Detail Component', () => {
    let comp: KeywordDetailComponent;
    let fixture: ComponentFixture<KeywordDetailComponent>;
    const route = ({ data: of({ keyword: new Keyword(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [KeywordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(KeywordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KeywordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load keyword on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.keyword).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
