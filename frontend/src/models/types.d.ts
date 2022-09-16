import {Semester} from "../constants/constants";

interface IQueueEntity {
    id: number;
    createdDate: string;
    name: string;
    subject: ISubject;
    placement: IPlacement;
    comment: string | null;
    studyYear: string;
}

interface ISubject {
    id: number;
    createdDate: string;
    name: string;
    semester: Semester;
}

interface IPlacement {
    id: number;
    createdDate: string;
    name: string;
    number: number;
}

interface IHyperlink {
    url: string;
    text: string;
}

