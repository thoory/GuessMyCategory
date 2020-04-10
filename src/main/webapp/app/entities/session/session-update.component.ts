import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { IVideo } from 'app/shared/model/video.model';
import { VideoService } from 'app/entities/video/video.service';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from 'app/entities/game/game.service';

type SelectableEntity = IVideo | IGame;

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  videos: IVideo[] = [];
  games: IGame[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    maxAnswer: [],
    maxTime: [],
    video: [],
    games: []
  });

  constructor(
    protected sessionService: SessionService,
    protected videoService: VideoService,
    protected gameService: GameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.videoService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IVideo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVideo[]) => {
          if (!session.video || !session.video.id) {
            this.videos = resBody;
          } else {
            this.videoService
              .find(session.video.id)
              .pipe(
                map((subRes: HttpResponse<IVideo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVideo[]) => (this.videos = concatRes));
          }
        });

      this.gameService.query().subscribe((res: HttpResponse<IGame[]>) => (this.games = res.body || []));
    });
  }

  updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      dateCreated: session.dateCreated,
      maxAnswer: session.maxAnswer,
      maxTime: session.maxTime,
      video: session.video,
      games: session.games
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      maxAnswer: this.editForm.get(['maxAnswer'])!.value,
      maxTime: this.editForm.get(['maxTime'])!.value,
      video: this.editForm.get(['video'])!.value,
      games: this.editForm.get(['games'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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
