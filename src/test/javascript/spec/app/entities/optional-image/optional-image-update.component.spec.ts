import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { OptionalImageUpdateComponent } from 'app/entities/optional-image/optional-image-update.component';
import { OptionalImageService } from 'app/entities/optional-image/optional-image.service';
import { OptionalImage } from 'app/shared/model/optional-image.model';

describe('Component Tests', () => {
  describe('OptionalImage Management Update Component', () => {
    let comp: OptionalImageUpdateComponent;
    let fixture: ComponentFixture<OptionalImageUpdateComponent>;
    let service: OptionalImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [OptionalImageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OptionalImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptionalImageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptionalImageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptionalImage(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptionalImage();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
