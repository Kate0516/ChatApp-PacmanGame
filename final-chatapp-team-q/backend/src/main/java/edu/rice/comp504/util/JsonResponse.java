package edu.rice.comp504.util;

public class JsonResponse {
    public boolean success;
    public Object payload;
    public String message;

    @HiddenInGson
    public int statusCode;

    /**
     * Constructor of JsonResponse.
     *
     * @param success if the response is success
     * @param data    data of resp
     * @param message resp message. if success is true, it's preferable that message == ""
     */
    public JsonResponse(boolean success, Object data, String message) {
        this.success = success;
        this.payload = data;
        this.message = message;
        this.statusCode = 200;
    }


    /**
     * Constructor of JsonResponse.
     *
     * @param success    if the response is success
     * @param data       data of resp
     * @param message    resp message. if success is true, it's preferable that message == ""
     * @param statusCode http response status code
     */
    public JsonResponse(boolean success, Object data, String message, int statusCode) {
        this.success = success;
        this.payload = data;
        this.message = message;
        this.statusCode = statusCode;
    }
}
