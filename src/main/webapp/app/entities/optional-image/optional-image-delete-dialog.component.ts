import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from './optional-image.service';

@Component({
  templateUrl: './optional-image-delete-dialog.component.html'
})
export class OptionalImageDeleteDialogComponent {
  optionalImage?: IOptionalImage;

  constructor(
    protected optionalImageService: OptionalImageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optionalImageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optionalImageListModification');
      this.activeModal.close();
    });
  }
}
