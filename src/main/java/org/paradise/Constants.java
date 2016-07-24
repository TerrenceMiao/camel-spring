package org.paradise;

/**
 * Created by terrence on 9/06/2016.
 */
public class Constants {

    public static final String OK = "OK";

    public static final String CURRENT_VERSION = "/v1";

    public static final String HEALTH_CHECK_PATH = CURRENT_VERSION + "/health";
    public static final String PDF_PATH = CURRENT_VERSION + "/pdf";

    public static final String REDIS_KEY = "camelKey";
    public static final String REDIS_FIELD = "camelField";

    private Constants() {
    }

}
