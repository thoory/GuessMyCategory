import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGame } from 'app/shared/model/game.model';

type EntityResponseType = HttpResponse<IGame>;
type EntityArrayResponseType = HttpResponse<IGame[]>;

@Injectable({ providedIn: 'root' })
export class GameService {
  public resourceUrl = SERVER_API_URL + 'api/games';

  constructor(protected http: HttpClient) {}

  create(game: IGame): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(game);
    return this.http
      .post<IGame>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(game: IGame): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(game);
    return this.http
      .put<IGame>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGame>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGame[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(game: IGame): IGame {
    const copy: IGame = Object.assign({}, game, {
      dateCreated: game.dateCreated && game.dateCreated.isValid() ? game.dateCreated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((game: IGame) => {
        game.dateCreated = game.dateCreated ? moment(game.dateCreated) : undefined;
      });
    }
    return res;
  }
}
