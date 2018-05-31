package com.shp.commons.web.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	public static String object2JsonString(final Object object) throws JsonProcessingException{
		return mapper.writeValueAsString(object);
	}
	public static JsonNode String2JsonNode(String str) throws JsonProcessingException, IOException{
		return  mapper.readTree(str);
	}
}
