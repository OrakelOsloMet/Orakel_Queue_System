import {AuthActionTypes, PlacementActionTypes, QueueActionTypes, SubjectActionTypes} from "./actions/actionTypes";
import {IPlacement, IQueueEntity, ISubject} from "../models/types";

interface IUser {
    token: string | null;
    userId: string | null;
    roles: Array<string>
}

interface APIDependentState {
    error: string | null;
    loading: boolean;
}

type AuthState = {
    user: IUser | null;
    error: string | null;
    loading: boolean;
}

interface QueueState extends APIDependentState {
    queueData: Array<IQueueEntity>;
}

interface SubjectState extends APIDependentState {
    currentSubjectData: Array<ISubject>;
    allSubjectData: Array<ISubject>;
}

interface PlacementState extends APIDependentState {
    placementData: Array<IPlacement>;
}

type FetchAction = {}

type QueueAction = {
    type: QueueActionTypes;
    queueData?: Array<IQueueEntity>;
    subjectData?: Array<ISubject>;
    error?: string;
}

type AuthAction = {
    type: AuthActionTypes;
    user?: IUser;
    error?: string;
}

type SubjectAction = {
    type: SubjectActionTypes;
    allSubjectData?: Array<ISubject>;
    currentSubjectData?: Array<ISubject>;
    error?: string;
}

type PlacementAction = {
    type: PlacementActionTypes;
    placementData?: Array<IPlacement>
    error?: string;
}


type QueueDispatch = (args: QueueAction | FetchAction) => QueueAction | FetchAction;
type AuthDispatch = (args: AuthAction) => AuthAction;
type SubjectDispatch = (args: SubjectAction | FetchAction) => SubjectAction | FetchAction;
type PlacementDispatch = (args: PlacementAction | FetchAction) => PlacementAction | FetchAction