import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOptionalImage, OptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from './optional-image.service';

@Component({
  selector: 'jhi-optional-image-update',
  templateUrl: './optional-image-update.component.html'
})
export class OptionalImageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    link: [null, [Validators.required]]
  });

  constructor(protected optionalImageService: OptionalImageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionalImage }) => {
      this.updateForm(optionalImage);
    });
  }

  updateForm(optionalImage: IOptionalImage): void {
    this.editForm.patchValue({
      id: optionalImage.id,
      link: optionalImage.link
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optionalImage = this.createFromForm();
    if (optionalImage.id !== undefined) {
      this.subscribeToSaveResponse(this.optionalImageService.update(optionalImage));
    } else {
      this.subscribeToSaveResponse(this.optionalImageService.create(optionalImage));
    }
  }

  private createFromForm(): IOptionalImage {
    return {
      ...new OptionalImage(),
      id: this.editForm.get(['id'])!.value,
      link: this.editForm.get(['link'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionalImage>>): void {
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
