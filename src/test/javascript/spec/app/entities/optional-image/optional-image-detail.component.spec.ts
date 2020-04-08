import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { OptionalImageDetailComponent } from 'app/entities/optional-image/optional-image-detail.component';
import { OptionalImage } from 'app/shared/model/optional-image.model';

describe('Component Tests', () => {
  describe('OptionalImage Management Detail Component', () => {
    let comp: OptionalImageDetailComponent;
    let fixture: ComponentFixture<OptionalImageDetailComponent>;
    const route = ({ data: of({ optionalImage: new OptionalImage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [OptionalImageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OptionalImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptionalImageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optionalImage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optionalImage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
