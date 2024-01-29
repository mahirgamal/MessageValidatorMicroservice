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


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    private String schema;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonSchema jschema;

    public Function() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("LEI/eventCore.json");
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            schema = sb.toString();
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
            jschema = schemaFactory.getSchema(schema);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            JsonNode json = objectMapper.readTree(query);
            Set<ValidationMessage> validationResult = jschema.validate(json);

            StringBuilder errorMessageBuilder = new StringBuilder();
            if (validationResult.isEmpty()) {
                errorMessageBuilder.append("valid");
                return request.createResponseBuilder(HttpStatus.OK)
                        .body(errorMessageBuilder.toString())
                        .build();
            } else {
                validationResult.forEach(vm -> errorMessageBuilder.append(vm.getMessage()).append("\r"));
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


