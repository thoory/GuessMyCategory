import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GuessMyCategoryTestModule } from '../../../test.module';
import { GameComponent } from 'app/entities/game/game.component';
import { GameService } from 'app/entities/game/game.service';
import { Game } from 'app/shared/model/game.model';

describe('Component Tests', () => {
  describe('Game Management Component', () => {
    let comp: GameComponent;
    let fixture: ComponentFixture<GameComponent>;
    let service: GameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GuessMyCategoryTestModule],
        declarations: [GameComponent]
      })
        .overrideTemplate(GameComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GameComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GameService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Game(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.games && comp.games[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
