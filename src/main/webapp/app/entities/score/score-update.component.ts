import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScore, Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

type SelectableEntity = IUser | ISession;

@Component({
  selector: 'jhi-score-update',
  templateUrl: './score-update.component.html'
})
export class ScoreUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  sessions: ISession[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    answerTimeAvg: [],
    correctAnswer: [],
    propositionTotal: [],
    user: [],
    session: []
  });

  constructor(
    protected scoreService: ScoreService,
    protected userService: UserService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ score }) => {
      this.updateForm(score);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.sessionService.query().subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
    });
  }

  updateForm(score: IScore): void {
    this.editForm.patchValue({
      id: score.id,
      dateCreated: score.dateCreated,
      answerTimeAvg: score.answerTimeAvg,
      correctAnswer: score.correctAnswer,
      propositionTotal: score.propositionTotal,
      user: score.user,
      session: score.session
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const score = this.createFromForm();
    if (score.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreService.update(score));
    } else {
      this.subscribeToSaveResponse(this.scoreService.create(score));
    }
  }

  private createFromForm(): IScore {
    return {
      ...new Score(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      answerTimeAvg: this.editForm.get(['answerTimeAvg'])!.value,
      correctAnswer: this.editForm.get(['correctAnswer'])!.value,
      propositionTotal: this.editForm.get(['propositionTotal'])!.value,
      user: this.editForm.get(['user'])!.value,
      session: this.editForm.get(['session'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>): void {
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
