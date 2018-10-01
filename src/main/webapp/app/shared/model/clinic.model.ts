import { ITurn } from 'app/shared/model//turn.model';

export interface IClinic {
    id?: number;
    name?: string;
    email?: string;
    address?: string;
    phone?: string;
    website?: string;
    note?: string;
    turns?: ITurn[];
}

export class Clinic implements IClinic {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public address?: string,
        public phone?: string,
        public website?: string,
        public note?: string,
        public turns?: ITurn[]
    ) {}
}
