import { ITurn } from 'app/shared/model//turn.model';

export interface ITurnType {
    id?: number;
    name?: string;
    turns?: ITurn[];
}

export class TurnType implements ITurnType {
    constructor(public id?: number, public name?: string, public turns?: ITurn[]) {}
}
