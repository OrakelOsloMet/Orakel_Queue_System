
/* ----- Authentication ------ */
export const LOCAL_STORAGE_USER = "user";

/* ----- API ------ */
export const API_BASE_PATH = "https://orakelqueueservice.herokuapp.com/api/" //"/api/"

//LandingPage
export const QUEUE_PATH = "queue/";
export const CONFIRM_DONE_PATH = QUEUE_PATH + "confirmdone/";

//Subjects
export const SUBJECTS_PATH = "subjects/";
export const CURRENT_SUBJECTS_PATH = SUBJECTS_PATH + "current/";
export const EDIT_SUBJECT_PATH = SUBJECTS_PATH + "edit/";
export const DELETE_SUBJECT_PATH = SUBJECTS_PATH + "delete/";

//Placements
export const PLACEMENTS_PATH = "placements/";
export const EDIT_PLACEMENT_PATH = PLACEMENTS_PATH + "edit/";
export const DELETE_PLACEMENT_PATH = PLACEMENTS_PATH + "delete/";

//Auth
export const AUTH_PATH = "auth/";
export const LOGIN_PATH = AUTH_PATH + "signin";
export const CHECK_TOKEN_PATH = AUTH_PATH + "isTokenValid";

//Resources
export const RESOURCES_BASE_PATH = "resources/";
export const USER_GUIDE_PATH = API_BASE_PATH + RESOURCES_BASE_PATH + "userguide/"; //Axios-instance is configured to add API_BASE_PATH, but retrieval of the userguide is done without axios.
export const QUEUE_EXPORT_PATH = RESOURCES_BASE_PATH + "queuedata/"

/* ----- Routing ----- */
export const INDEX_ROUTE = "/Orakel_Queue_Client/";
export const ADMIN_ROUTE = INDEX_ROUTE + "Admin";

/* ----- Form Elements ----- */
export enum FormElementType {
    SELECT = "select",
    TEXT = "text",
    VALIDATED_TEXT = "validated_text",
    RADIO = "radio",
}

/* ----- Semesters ----- */
export enum Semester {
    SPRING = "Spring",
    AUTUMN = "Autumn",
}


//TODO Add all hardcoded string values to this file
