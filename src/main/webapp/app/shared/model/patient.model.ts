import { IClinicHistory } from 'app/shared/model//clinic-history.model';
import { ITurn } from 'app/shared/model//turn.model';

export interface IPatient {
    id?: number;
    fullName?: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    insuranceMutual?: string;
    dni?: string;
    numberAfiliated?: string;
    note?: string;
    clinicHistories?: IClinicHistory[];
    turns?: ITurn[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public fullName?: string,
        public email?: string,
        public phone?: string,
        public mobile?: string,
        public address?: string,
        public insuranceMutual?: string,
        public dni?: string,
        public numberAfiliated?: string,
        public note?: string,
        public clinicHistories?: IClinicHistory[],
        public turns?: ITurn[]
    ) {}
}
