import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GuessMyCategorySharedModule } from 'app/shared/shared.module';
import { OptionalImageComponent } from './optional-image.component';
import { OptionalImageDetailComponent } from './optional-image-detail.component';
import { OptionalImageUpdateComponent } from './optional-image-update.component';
import { OptionalImageDeleteDialogComponent } from './optional-image-delete-dialog.component';
import { optionalImageRoute } from './optional-image.route';

@NgModule({
  imports: [GuessMyCategorySharedModule, RouterModule.forChild(optionalImageRoute)],
  declarations: [OptionalImageComponent, OptionalImageDetailComponent, OptionalImageUpdateComponent, OptionalImageDeleteDialogComponent],
  entryComponents: [OptionalImageDeleteDialogComponent]
})
export class GuessMyCategoryOptionalImageModule {}
