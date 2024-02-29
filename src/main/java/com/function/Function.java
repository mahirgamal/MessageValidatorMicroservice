package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

import com.domain.MessageValidator;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    public Function() {

    }

    /**
     * This function listens at endpoint "/api/validate". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/validate
     * 2. curl {your host}/api/validate?name=HTTP%20Query
     */
    @FunctionName("validate")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String query = request.getBody().orElse(null);

        if (query == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a body")
                    .build();
        }

        try {
            
            MessageValidator messageValidator=new MessageValidator(query);

            StringBuilder errorMessageBuilder = new StringBuilder();
            if (messageValidator.getValidationResult().isEmpty()) {
                errorMessageBuilder.append("valid");
                return request.createResponseBuilder(HttpStatus.OK)
                        .body(errorMessageBuilder.toString())
                        .build();
            } else {
                messageValidator.getValidationResult().forEach(vm -> errorMessageBuilder.append(vm.getMessage()).append("\r"));
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body(errorMessageBuilder.toString())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getClass().getName();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorMessage)
                    .build();
        }
    }
}


