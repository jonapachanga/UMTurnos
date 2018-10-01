import { Moment } from 'moment';
import { ITurnType } from 'app/shared/model//turn-type.model';
import { IClinic } from 'app/shared/model//clinic.model';
import { IPatient } from 'app/shared/model//patient.model';
import { IUser } from 'app/core/user/user.model';

export interface ITurn {
    id?: number;
    dateAndHour?: Moment;
    turnType?: ITurnType;
    clinic?: IClinic;
    patient?: IPatient;
    user?: IUser;
}

export class Turn implements ITurn {
    constructor(
        public id?: number,
        public dateAndHour?: Moment,
        public turnType?: ITurnType,
        public clinic?: IClinic,
        public patient?: IPatient,
        public user?: IUser
    ) {}
}
