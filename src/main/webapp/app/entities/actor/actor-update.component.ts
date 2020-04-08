import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActor, Actor } from 'app/shared/model/actor.model';
import { ActorService } from './actor.service';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from 'app/entities/optional-image/optional-image.service';

@Component({
  selector: 'jhi-actor-update',
  templateUrl: './actor-update.component.html'
})
export class ActorUpdateComponent implements OnInit {
  isSaving = false;
  optionalimages: IOptionalImage[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    popularity: [],
    contry: [],
    link: [],
    images: []
  });

  constructor(
    protected actorService: ActorService,
    protected optionalImageService: OptionalImageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actor }) => {
      this.updateForm(actor);

      this.optionalImageService.query().subscribe((res: HttpResponse<IOptionalImage[]>) => (this.optionalimages = res.body || []));
    });
  }

  updateForm(actor: IActor): void {
    this.editForm.patchValue({
      id: actor.id,
      name: actor.name,
      popularity: actor.popularity,
      contry: actor.contry,
      link: actor.link,
      images: actor.images
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actor = this.createFromForm();
    if (actor.id !== undefined) {
      this.subscribeToSaveResponse(this.actorService.update(actor));
    } else {
      this.subscribeToSaveResponse(this.actorService.create(actor));
    }
  }

  private createFromForm(): IActor {
    return {
      ...new Actor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      popularity: this.editForm.get(['popularity'])!.value,
      contry: this.editForm.get(['contry'])!.value,
      link: this.editForm.get(['link'])!.value,
      images: this.editForm.get(['images'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActor>>): void {
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

  trackById(index: number, item: IOptionalImage): any {
    return item.id;
  }
}
