package edu.rice.comp504.util;

public class JsonStatusResponse {
    private boolean success;
    private Object data;
    private String message;
    private int statusCode;

    /**
     * Constructor of JSONStatusResponse.
     *
     * @param success    if the response is success
     * @param data       data of resp
     * @param message    resp message
     * @param statusCode the status code
     */
    public JsonStatusResponse(boolean success, Object data, String message, int statusCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     * Get the status code for response.
     *
     * @return status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}
