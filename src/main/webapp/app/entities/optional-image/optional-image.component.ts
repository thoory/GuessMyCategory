import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from './optional-image.service';
import { OptionalImageDeleteDialogComponent } from './optional-image-delete-dialog.component';

@Component({
  selector: 'jhi-optional-image',
  templateUrl: './optional-image.component.html'
})
export class OptionalImageComponent implements OnInit, OnDestroy {
  optionalImages?: IOptionalImage[];
  eventSubscriber?: Subscription;

  constructor(
    protected optionalImageService: OptionalImageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.optionalImageService.query().subscribe((res: HttpResponse<IOptionalImage[]>) => (this.optionalImages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOptionalImages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOptionalImage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOptionalImages(): void {
    this.eventSubscriber = this.eventManager.subscribe('optionalImageListModification', () => this.loadAll());
  }

  delete(optionalImage: IOptionalImage): void {
    const modalRef = this.modalService.open(OptionalImageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.optionalImage = optionalImage;
  }
}
