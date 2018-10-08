import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';

export interface IClinicHistory {
    id?: number;
    dateAndHour?: Moment;
    issue?: string;
    history?: string;
    patient?: IPatient;
}

export class ClinicHistory implements IClinicHistory {
    constructor(
        public id?: number,
        public dateAndHour?: Moment,
        public issue?: string,
        public history?: string,
        public patient?: IPatient
    ) {}
}
