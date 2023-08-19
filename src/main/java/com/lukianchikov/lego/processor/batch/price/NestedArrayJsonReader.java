package com.lukianchikov.lego.processor.batch.price;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/*
 * This class follows the structure and functions similar to JacksonJsonObjectReader, with
 * the difference that it expects an object as root node, instead of an array.
 */
public class NestedArrayJsonReader<T> implements JsonObjectReader<T> {

    Logger logger = Logger.getLogger(NestedArrayJsonReader.class.getName());

    ObjectMapper mapper = new ObjectMapper();
    private JsonParser jsonParser;
    private InputStream inputStream;

    private ArrayNode targetNode;
    private Class<T> targetType;
    private String targetPath;

    public NestedArrayJsonReader(Class<T> targetType, String targetPath) {
        super();
        this.targetType = targetType;
        this.targetPath = targetPath;
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public ArrayNode getDatasetNode() {
        return targetNode;
    }

    /*
     * JsonObjectReader interface has an empty default method and must be implemented in this case to set
     * the mapper and the parser
     */
    @Override
    public void open(Resource resource) throws Exception {
        logger.info("Opening json object reader");
        this.inputStream = resource.getInputStream();
        JsonNode jsonNode = this.mapper.readTree(this.inputStream).findPath(targetPath);
        if (!jsonNode.isMissingNode()) {
            this.jsonParser = startArrayParser(jsonNode);
            logger.info("Reader open with parser reference: " + this.jsonParser);
            this.targetNode = (ArrayNode) jsonNode; // for testing purposes
        } else {
            logger.severe("Couldn't read target node " + this.targetPath);
            throw new RuntimeException("Unreachable node");
        }
    }


    @Override
    public T read() throws Exception {
        try {
            if (this.jsonParser.nextToken() == JsonToken.START_OBJECT) {
                T result = this.mapper.readValue(this.jsonParser, this.targetType);
                logger.info("Object read: " + result.hashCode());
                return result;
            }
        } catch (IOException e) {
            throw new ParseException("Unable to read next JSON object", e);
        }
        return null;
    }

    /**
     * Creates a new parser from an array node
     */
    private JsonParser startArrayParser(JsonNode jsonArrayNode) throws IOException {
        JsonParser jsonParser = this.mapper.getFactory().createParser(jsonArrayNode.toString());
        if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
            return jsonParser;
        } else {
            throw new RuntimeException("Invalid array node");
        }
    }

    @Override
    public void close() throws Exception {
        this.inputStream.close();
        this.jsonParser.close();
    }

}