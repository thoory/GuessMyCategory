import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionalImage } from 'app/shared/model/optional-image.model';

@Component({
  selector: 'jhi-optional-image-detail',
  templateUrl: './optional-image-detail.component.html'
})
export class OptionalImageDetailComponent implements OnInit {
  optionalImage: IOptionalImage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionalImage }) => (this.optionalImage = optionalImage));
  }

  previousState(): void {
    window.history.back();
  }
}
