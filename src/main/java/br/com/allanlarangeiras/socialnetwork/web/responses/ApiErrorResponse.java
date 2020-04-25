package br.com.allanlarangeiras.socialnetwork.web.responses;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private String message;

    public static ApiErrorResponse build(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setMessage(message);
        return apiErrorResponse;
    }
}
