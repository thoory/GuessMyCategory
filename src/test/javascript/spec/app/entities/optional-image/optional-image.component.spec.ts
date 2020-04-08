import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { OptionalImageComponent } from 'app/entities/optional-image/optional-image.component';
import { OptionalImageService } from 'app/entities/optional-image/optional-image.service';
import { OptionalImage } from 'app/shared/model/optional-image.model';

describe('Component Tests', () => {
  describe('OptionalImage Management Component', () => {
    let comp: OptionalImageComponent;
    let fixture: ComponentFixture<OptionalImageComponent>;
    let service: OptionalImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [OptionalImageComponent]
      })
        .overrideTemplate(OptionalImageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptionalImageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptionalImageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OptionalImage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.optionalImages && comp.optionalImages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
