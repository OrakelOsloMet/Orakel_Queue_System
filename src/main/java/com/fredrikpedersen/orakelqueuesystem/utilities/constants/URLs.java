package com.fredrikpedersen.orakelqueuesystem.utilities.constants;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public class URLs {

    //Authentication
    public static final String AUTHENTICATION_BASE_URL = "/api/auth/";
    public static final String AUTHENTICATION_SIGN_IN_URL = "signin";
    public static final String AUTHENTICATION_TOKEN_VALID_URL = "isTokenValid";

    //Queue
    public static final String QUEUE_BASE_URL = "/api/queue/";
    public static final String QUEUE_CONFIRM_DONE_URL = "confirmdone/";

    //Subject
    public static final String SUBJECT_BASE_URL = "/api/subjects/";

    //Resources
    public static final String RESOURCES_BASE_URL = "/api/resources/";
    public static final String USER_GUIDE_URL = "userguide";
    public static final String QUEUE_DATA_EXPORT_URL = "queuedata";
}
