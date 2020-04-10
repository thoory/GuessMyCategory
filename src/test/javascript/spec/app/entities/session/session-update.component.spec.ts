import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { SessionUpdateComponent } from 'app/entities/session/session-update.component';
import { SessionService } from 'app/entities/session/session.service';
import { Session } from 'app/shared/model/session.model';

describe('Component Tests', () => {
  describe('Session Management Update Component', () => {
    let comp: SessionUpdateComponent;
    let fixture: ComponentFixture<SessionUpdateComponent>;
    let service: SessionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [SessionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SessionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SessionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SessionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Session(123);
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
        const entity = new Session();
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
