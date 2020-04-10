import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { ScoreDetailComponent } from 'app/entities/score/score-detail.component';
import { Score } from 'app/shared/model/score.model';

describe('Component Tests', () => {
  describe('Score Management Detail Component', () => {
    let comp: ScoreDetailComponent;
    let fixture: ComponentFixture<ScoreDetailComponent>;
    const route = ({ data: of({ score: new Score(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [ScoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load score on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.score).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
