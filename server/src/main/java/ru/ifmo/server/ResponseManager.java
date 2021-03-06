package ru.ifmo.server;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.EnumMap;

public class ResponseManager {

    private enum JSON_ELEMENTS {
        ERROR_CODE,
        FAILURE_MSG,
        RESPONSE
    }

    private static final EnumMap<JSON_ELEMENTS, String> jsonElements;

    static {
        jsonElements = new EnumMap<JSON_ELEMENTS, String>(JSON_ELEMENTS.class) {{
           put(JSON_ELEMENTS.ERROR_CODE, "\"error_code\" : ");
           put(JSON_ELEMENTS.FAILURE_MSG, "\"failure_msg\" : ");
           put(JSON_ELEMENTS.RESPONSE, "\"response\" : ");
        }};
    }

    private static final Gson gson = new Gson();

    public static final ResponseEntity<String> simpleResponse = new ResponseEntity<>(
            "{ " + jsonElements.get(JSON_ELEMENTS.RESPONSE) + 1 + " }",
            HttpStatus.OK
    );

    public final static ResponseEntity<String> tokenExpiredResponse = new ResponseEntity<>(
            "{ " + jsonElements.get(JSON_ELEMENTS.ERROR_CODE) + "-1 , " +
                    jsonElements.get(JSON_ELEMENTS.FAILURE_MSG) + "\"Access token has expired or invalid\" }",
            HttpStatus.UNAUTHORIZED);

    public static <T> ResponseEntity<String> createResponse(T t) {
        final StringBuilder builder = new StringBuilder();
        final String serialized = gson.toJson(t);
        builder.append("{ ");
        builder.append(jsonElements.get(JSON_ELEMENTS.RESPONSE));
        builder.append(serialized);
        return new ResponseEntity<>(builder.append(" }").toString(), HttpStatus.OK);
    }

    public static ResponseEntity<String> createResponse(Exception e, int errorCode) {
        final StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        builder.append(jsonElements.get(JSON_ELEMENTS.ERROR_CODE)).append(errorCode).append(',');
        builder.append(jsonElements.get(JSON_ELEMENTS.FAILURE_MSG)).append('"').append(e.getMessage()).append('"');
        return new ResponseEntity<>(builder.append(" }").toString(), HttpStatus.BAD_REQUEST);
    }
}
