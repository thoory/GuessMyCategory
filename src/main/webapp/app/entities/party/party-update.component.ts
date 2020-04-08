import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParty, Party } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

@Component({
  selector: 'jhi-party-update',
  templateUrl: './party-update.component.html'
})
export class PartyUpdateComponent implements OnInit {
  isSaving = false;
  sessions: ISession[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    code: [null, [Validators.required]],
    maxPlayer: [],
    maxVideoTime: [],
    sessions: []
  });

  constructor(
    protected partyService: PartyService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ party }) => {
      this.updateForm(party);

      this.sessionService.query().subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
    });
  }

  updateForm(party: IParty): void {
    this.editForm.patchValue({
      id: party.id,
      dateCreated: party.dateCreated,
      code: party.code,
      maxPlayer: party.maxPlayer,
      maxVideoTime: party.maxVideoTime,
      sessions: party.sessions
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const party = this.createFromForm();
    if (party.id !== undefined) {
      this.subscribeToSaveResponse(this.partyService.update(party));
    } else {
      this.subscribeToSaveResponse(this.partyService.create(party));
    }
  }

  private createFromForm(): IParty {
    return {
      ...new Party(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      code: this.editForm.get(['code'])!.value,
      maxPlayer: this.editForm.get(['maxPlayer'])!.value,
      maxVideoTime: this.editForm.get(['maxVideoTime'])!.value,
      sessions: this.editForm.get(['sessions'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParty>>): void {
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

  trackById(index: number, item: ISession): any {
    return item.id;
  }
}
