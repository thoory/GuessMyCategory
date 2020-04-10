import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVideo, Video } from 'app/shared/model/video.model';
import { VideoService } from './video.service';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from 'app/entities/optional-image/optional-image.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor/actor.service';
import { IKeyword } from 'app/shared/model/keyword.model';
import { KeywordService } from 'app/entities/keyword/keyword.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';

type SelectableEntity = IOptionalImage | IActor | IKeyword | ITag;

type SelectableManyToManyEntity = IActor | IKeyword | ITag;

@Component({
  selector: 'jhi-video-update',
  templateUrl: './video-update.component.html'
})
export class VideoUpdateComponent implements OnInit {
  isSaving = false;
  optionalimages: IOptionalImage[] = [];
  actors: IActor[] = [];
  keywords: IKeyword[] = [];
  tags: ITag[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreated: [null, [Validators.required]],
    iframe: [null, [Validators.required]],
    title: [null, [Validators.required]],
    duration: [null, [Validators.required]],
    view: [],
    like: [],
    dislike: [],
    optionalImages: [],
    actors: [],
    keywords: [],
    tags: []
  });

  constructor(
    protected videoService: VideoService,
    protected optionalImageService: OptionalImageService,
    protected actorService: ActorService,
    protected keywordService: KeywordService,
    protected tagService: TagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ video }) => {
      this.updateForm(video);

      this.optionalImageService.query().subscribe((res: HttpResponse<IOptionalImage[]>) => (this.optionalimages = res.body || []));

      this.actorService.query().subscribe((res: HttpResponse<IActor[]>) => (this.actors = res.body || []));

      this.keywordService.query().subscribe((res: HttpResponse<IKeyword[]>) => (this.keywords = res.body || []));

      this.tagService.query().subscribe((res: HttpResponse<ITag[]>) => (this.tags = res.body || []));
    });
  }

  updateForm(video: IVideo): void {
    this.editForm.patchValue({
      id: video.id,
      dateCreated: video.dateCreated,
      iframe: video.iframe,
      title: video.title,
      duration: video.duration,
      view: video.view,
      like: video.like,
      dislike: video.dislike,
      optionalImages: video.optionalImages,
      actors: video.actors,
      keywords: video.keywords,
      tags: video.tags
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const video = this.createFromForm();
    if (video.id !== undefined) {
      this.subscribeToSaveResponse(this.videoService.update(video));
    } else {
      this.subscribeToSaveResponse(this.videoService.create(video));
    }
  }

  private createFromForm(): IVideo {
    return {
      ...new Video(),
      id: this.editForm.get(['id'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      iframe: this.editForm.get(['iframe'])!.value,
      title: this.editForm.get(['title'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      view: this.editForm.get(['view'])!.value,
      like: this.editForm.get(['like'])!.value,
      dislike: this.editForm.get(['dislike'])!.value,
      optionalImages: this.editForm.get(['optionalImages'])!.value,
      actors: this.editForm.get(['actors'])!.value,
      keywords: this.editForm.get(['keywords'])!.value,
      tags: this.editForm.get(['tags'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideo>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
