package br.com.allanlarangeiras.socialnetwork.web.responses;

import lombok.Data;

@Data
public class ApiError {

    private String message;

    public static ApiError build(String message) {
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        return apiError;
    }
}
