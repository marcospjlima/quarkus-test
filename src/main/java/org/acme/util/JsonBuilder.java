package org.acme.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class JsonBuilder {
	
	private static final int MAX_SIZE = 5000;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonBuilder(CustomModuleCustomizer moduleCustomizer) {
        moduleCustomizer.customize(objectMapper);
    }

    public final <E> String toJson(E object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    public final <E> String toJson(List<E> list) throws IOException {
        return objectMapper.writeValueAsString(list);
    }

    public final <E> E fromStream(final InputStream inputStream, Class<E> clazz) throws IOException {
        return fromJson(readStream(inputStream), clazz);
    }

    public final <E> E fromJson(final String json, Class<E> clazz) throws IOException {
        return objectMapper.readValue(json.getBytes(), clazz);
    }

    public final <E> E fromJsonFile(final String jsonPath, Class<E> clazz) throws IOException {
        return objectMapper.readValue(new File(jsonPath), clazz);
    }
    
    public final <E> List<E> listFromStream(final InputStream inputStream, Class<E> clazz) throws IOException {
        return objectMapper.readValue(readStream(inputStream), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
    
    public final <E> List<E> listFromJson(final String json, Class<E> clazz) throws IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public final <E> List<E> listFromJsonFile(final String jsonPath, Class<E> clazz) throws IOException {
        return objectMapper.readValue(new File(jsonPath), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public final ObjectNode createObjectBuilder() {
        return objectMapper.createObjectNode();
    }

    public final ObjectNode createErrorObject(final String errorMsg, final Integer statusCode) {
        return createObjectBuilder().put("error", errorMsg).put("code", statusCode);
    }
    
    private static String readStream(InputStream in) throws IOException {
		byte[] data = new byte[MAX_SIZE];
		int r;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((r = in.read(data)) > 0) {
			out.write(data, 0, r);
		}
		return new String(out.toByteArray(), StandardCharsets.UTF_8);
	}

}
