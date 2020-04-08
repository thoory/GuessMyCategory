import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'video',
        loadChildren: () => import('./video/video.module').then(m => m.GuessMyCategoryVideoModule)
      },
      {
        path: 'actor',
        loadChildren: () => import('./actor/actor.module').then(m => m.GuessMyCategoryActorModule)
      },
      {
        path: 'optional-image',
        loadChildren: () => import('./optional-image/optional-image.module').then(m => m.GuessMyCategoryOptionalImageModule)
      },
      {
        path: 'keyword',
        loadChildren: () => import('./keyword/keyword.module').then(m => m.GuessMyCategoryKeywordModule)
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.GuessMyCategoryTagModule)
      },
      {
        path: 'party',
        loadChildren: () => import('./party/party.module').then(m => m.GuessMyCategoryPartyModule)
      },
      {
        path: 'player',
        loadChildren: () => import('./player/player.module').then(m => m.GuessMyCategoryPlayerModule)
      },
      {
        path: 'score',
        loadChildren: () => import('./score/score.module').then(m => m.GuessMyCategoryScoreModule)
      },
      {
        path: 'answer',
        loadChildren: () => import('./answer/answer.module').then(m => m.GuessMyCategoryAnswerModule)
      },
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.GuessMyCategorySessionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GuessMyCategoryEntityModule {}
