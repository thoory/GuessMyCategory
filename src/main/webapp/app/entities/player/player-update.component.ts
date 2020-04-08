import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPlayer, Player } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';
import { IScore } from 'app/shared/model/score.model';
import { ScoreService } from 'app/entities/score/score.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

type SelectableEntity = IScore | IParty;

@Component({
  selector: 'jhi-player-update',
  templateUrl: './player-update.component.html'
})
export class PlayerUpdateComponent implements OnInit {
  isSaving = false;
  scores: IScore[] = [];
  parties: IParty[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    name: [null, [Validators.required]],
    img: [],
    score: [],
    party: []
  });

  constructor(
    protected playerService: PlayerService,
    protected scoreService: ScoreService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ player }) => {
      this.updateForm(player);

      this.scoreService
        .query({ filter: 'player-is-null' })
        .pipe(
          map((res: HttpResponse<IScore[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IScore[]) => {
          if (!player.score || !player.score.id) {
            this.scores = resBody;
          } else {
            this.scoreService
              .find(player.score.id)
              .pipe(
                map((subRes: HttpResponse<IScore>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IScore[]) => (this.scores = concatRes));
          }
        });

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(player: IPlayer): void {
    this.editForm.patchValue({
      id: player.id,
      dateCreated: player.dateCreated,
      name: player.name,
      img: player.img,
      score: player.score,
      party: player.party
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const player = this.createFromForm();
    if (player.id !== undefined) {
      this.subscribeToSaveResponse(this.playerService.update(player));
    } else {
      this.subscribeToSaveResponse(this.playerService.create(player));
    }
  }

  private createFromForm(): IPlayer {
    return {
      ...new Player(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      name: this.editForm.get(['name'])!.value,
      img: this.editForm.get(['img'])!.value,
      score: this.editForm.get(['score'])!.value,
      party: this.editForm.get(['party'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayer>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
