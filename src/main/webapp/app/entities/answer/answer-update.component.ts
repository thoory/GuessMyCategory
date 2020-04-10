import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnswer, Answer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IScore } from 'app/shared/model/score.model';
import { ScoreService } from 'app/entities/score/score.service';

@Component({
  selector: 'jhi-answer-update',
  templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
  isSaving = false;
  scores: IScore[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    time: [],
    score: []
  });

  constructor(
    protected answerService: AnswerService,
    protected scoreService: ScoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answer }) => {
      this.updateForm(answer);

      this.scoreService.query().subscribe((res: HttpResponse<IScore[]>) => (this.scores = res.body || []));
    });
  }

  updateForm(answer: IAnswer): void {
    this.editForm.patchValue({
      id: answer.id,
      dateCreated: answer.dateCreated,
      time: answer.time,
      score: answer.score
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
      time: this.editForm.get(['time'])!.value,
      score: this.editForm.get(['score'])!.value
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

  trackById(index: number, item: IScore): any {
    return item.id;
  }
}
