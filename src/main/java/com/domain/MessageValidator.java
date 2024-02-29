package com.domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class MessageValidator {
    private String schema;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonSchema jschema;
    private Set<ValidationMessage> validationResult;

    public MessageValidator(String query) {
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

            JsonNode json = objectMapper.readTree(query);
            validationResult = jschema.validate(json);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public Set<ValidationMessage> getValidationResult() {
        return validationResult;
    }
}
