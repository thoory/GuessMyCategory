import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOptionalImage, OptionalImage } from 'app/shared/model/optional-image.model';
import { OptionalImageService } from './optional-image.service';
import { OptionalImageComponent } from './optional-image.component';
import { OptionalImageDetailComponent } from './optional-image-detail.component';
import { OptionalImageUpdateComponent } from './optional-image-update.component';

@Injectable({ providedIn: 'root' })
export class OptionalImageResolve implements Resolve<IOptionalImage> {
  constructor(private service: OptionalImageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptionalImage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((optionalImage: HttpResponse<OptionalImage>) => {
          if (optionalImage.body) {
            return of(optionalImage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OptionalImage());
  }
}

export const optionalImageRoute: Routes = [
  {
    path: '',
    component: OptionalImageComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'guessMyCategoryApp.optionalImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OptionalImageDetailComponent,
    resolve: {
      optionalImage: OptionalImageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'guessMyCategoryApp.optionalImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OptionalImageUpdateComponent,
    resolve: {
      optionalImage: OptionalImageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'guessMyCategoryApp.optionalImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OptionalImageUpdateComponent,
    resolve: {
      optionalImage: OptionalImageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'guessMyCategoryApp.optionalImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
