import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGame, Game } from 'app/shared/model/game.model';
import { GameService } from './game.service';

@Component({
  selector: 'jhi-game-update',
  templateUrl: './game-update.component.html'
})
export class GameUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    code: [null, [Validators.required]],
    maxUser: [],
    maxVideoTime: []
  });

  constructor(protected gameService: GameService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ game }) => {
      this.updateForm(game);
    });
  }

  updateForm(game: IGame): void {
    this.editForm.patchValue({
      id: game.id,
      dateCreated: game.dateCreated,
      code: game.code,
      maxUser: game.maxUser,
      maxVideoTime: game.maxVideoTime
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const game = this.createFromForm();
    if (game.id !== undefined) {
      this.subscribeToSaveResponse(this.gameService.update(game));
    } else {
      this.subscribeToSaveResponse(this.gameService.create(game));
    }
  }

  private createFromForm(): IGame {
    return {
      ...new Game(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      code: this.editForm.get(['code'])!.value,
      maxUser: this.editForm.get(['maxUser'])!.value,
      maxVideoTime: this.editForm.get(['maxVideoTime'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
