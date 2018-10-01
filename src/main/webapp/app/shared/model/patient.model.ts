import { ITurn } from 'app/shared/model//turn.model';

export interface IPatient {
    id?: number;
    name?: string;
    surName?: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    insuranceMutual?: string;
    dni?: string;
    numberAfiliated?: string;
    note?: string;
    turns?: ITurn[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public name?: string,
        public surName?: string,
        public email?: string,
        public phone?: string,
        public mobile?: string,
        public address?: string,
        public insuranceMutual?: string,
        public dni?: string,
        public numberAfiliated?: string,
        public note?: string,
        public turns?: ITurn[]
    ) {}
}
