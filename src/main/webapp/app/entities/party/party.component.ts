import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParty } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { PartyDeleteDialogComponent } from './party-delete-dialog.component';

@Component({
  selector: 'jhi-party',
  templateUrl: './party.component.html'
})
export class PartyComponent implements OnInit, OnDestroy {
  parties?: IParty[];
  eventSubscriber?: Subscription;

  constructor(protected partyService: PartyService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParty): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParties(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyListModification', () => this.loadAll());
  }

  delete(party: IParty): void {
    const modalRef = this.modalService.open(PartyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.party = party;
  }
}
