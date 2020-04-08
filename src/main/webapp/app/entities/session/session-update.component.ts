import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';
import { IKeyword } from 'app/shared/model/keyword.model';
import { KeywordService } from 'app/entities/keyword/keyword.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from 'app/entities/optional-image/optional-image.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor/actor.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer/answer.service';

type SelectableEntity = IPlayer | IKeyword | ITag | IOptionalImage | IActor | IAnswer;

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  players: IPlayer[] = [];
  keywords: IKeyword[] = [];
  tags: ITag[] = [];
  optionalimages: IOptionalImage[] = [];
  actors: IActor[] = [];
  answers: IAnswer[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    players: [],
    keywords: [],
    tags: [],
    optionalImages: [],
    actors: [],
    answers: []
  });

  constructor(
    protected sessionService: SessionService,
    protected playerService: PlayerService,
    protected keywordService: KeywordService,
    protected tagService: TagService,
    protected optionalImageService: OptionalImageService,
    protected actorService: ActorService,
    protected answerService: AnswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.playerService.query().subscribe((res: HttpResponse<IPlayer[]>) => (this.players = res.body || []));

      this.keywordService.query().subscribe((res: HttpResponse<IKeyword[]>) => (this.keywords = res.body || []));

      this.tagService.query().subscribe((res: HttpResponse<ITag[]>) => (this.tags = res.body || []));

      this.optionalImageService.query().subscribe((res: HttpResponse<IOptionalImage[]>) => (this.optionalimages = res.body || []));

      this.actorService.query().subscribe((res: HttpResponse<IActor[]>) => (this.actors = res.body || []));

      this.answerService.query().subscribe((res: HttpResponse<IAnswer[]>) => (this.answers = res.body || []));
    });
  }

  updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      dateCreated: session.dateCreated,
      players: session.players,
      keywords: session.keywords,
      tags: session.tags,
      optionalImages: session.optionalImages,
      actors: session.actors,
      answers: session.answers
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
      players: this.editForm.get(['players'])!.value,
      keywords: this.editForm.get(['keywords'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      optionalImages: this.editForm.get(['optionalImages'])!.value,
      actors: this.editForm.get(['actors'])!.value,
      answers: this.editForm.get(['answers'])!.value
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

  getSelected(selectedVals: IPlayer[], option: IPlayer): IPlayer {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
