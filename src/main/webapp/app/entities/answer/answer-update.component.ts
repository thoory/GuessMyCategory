import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAnswer, Answer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
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

type SelectableEntity = IPlayer | IKeyword | ITag | IOptionalImage | IActor;

@Component({
  selector: 'jhi-answer-update',
  templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
  isSaving = false;
  players: IPlayer[] = [];
  keywords: IKeyword[] = [];
  tags: ITag[] = [];
  optionalimages: IOptionalImage[] = [];
  actors: IActor[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    player: [],
    keywords: [],
    tags: [],
    optionalImages: [],
    actors: []
  });

  constructor(
    protected answerService: AnswerService,
    protected playerService: PlayerService,
    protected keywordService: KeywordService,
    protected tagService: TagService,
    protected optionalImageService: OptionalImageService,
    protected actorService: ActorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answer }) => {
      this.updateForm(answer);

      this.playerService
        .query({ filter: 'answer-is-null' })
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPlayer[]) => {
          if (!answer.player || !answer.player.id) {
            this.players = resBody;
          } else {
            this.playerService
              .find(answer.player.id)
              .pipe(
                map((subRes: HttpResponse<IPlayer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPlayer[]) => (this.players = concatRes));
          }
        });

      this.keywordService.query().subscribe((res: HttpResponse<IKeyword[]>) => (this.keywords = res.body || []));

      this.tagService.query().subscribe((res: HttpResponse<ITag[]>) => (this.tags = res.body || []));

      this.optionalImageService.query().subscribe((res: HttpResponse<IOptionalImage[]>) => (this.optionalimages = res.body || []));

      this.actorService.query().subscribe((res: HttpResponse<IActor[]>) => (this.actors = res.body || []));
    });
  }

  updateForm(answer: IAnswer): void {
    this.editForm.patchValue({
      id: answer.id,
      dateCreated: answer.dateCreated,
      player: answer.player,
      keywords: answer.keywords,
      tags: answer.tags,
      optionalImages: answer.optionalImages,
      actors: answer.actors
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const answer = this.createFromForm();
    if (answer.id !== undefined) {
      this.subscribeToSaveResponse(this.answerService.update(answer));
    } else {
      this.subscribeToSaveResponse(this.answerService.create(answer));
    }
  }

  private createFromForm(): IAnswer {
    return {
      ...new Answer(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      player: this.editForm.get(['player'])!.value,
      keywords: this.editForm.get(['keywords'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      optionalImages: this.editForm.get(['optionalImages'])!.value,
      actors: this.editForm.get(['actors'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>): void {
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
