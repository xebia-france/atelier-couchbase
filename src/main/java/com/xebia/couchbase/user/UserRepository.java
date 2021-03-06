package com.xebia.couchbase.user;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.transcoder.JsonTranscoder;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.xebia.couchbase.Configuration.publicotaurusBucket;
import static com.xebia.couchbase.Configuration.reinitConnection;

public class UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private static final String USER_DOCUMENT_PREFIX = "user::";
    public static final int DEFAULT_LOCK_TIME = 5;
    private final Gson gson;
    private final JsonTranscoder jsonTranscoder;
    private final CounterRepository counterRepository;

    public UserRepository() {
        jsonTranscoder = new JsonTranscoder();
        gson = new Gson();
        counterRepository = new CounterRepository();
    }

    //TODO Exercise 3.2
    public void insertUser(User user) {
        // get the user JsonDocument thanks to the userToDocument() method.

        //Insert the document thanks to the Configuration.publicotaurusBucket()
    }

    //TODO Exercise 3.3
    public JsonDocument findUser(String firstName, String lastName) {
        //TODO Exercise 5.2
        return null;
    }

    //TODO Exercise 4a
    public JsonDocument updateUser(JsonDocument user) {
        return null;
    }

    //TODO Exercise 4b
    public JsonDocument getAndLock(String firstName, String lastName) {
        return null;
    }

    //TODO Exercise 6
    public void insertManyUsers(List<User> users) {

    }

    // Exercise 3.1
    private JsonDocument userToDocument(User user) {
        // This method transforms user to document thanks to a Gson object and a JsonTranscoder object
        final JsonObject userJsonObject;
        try {
            userJsonObject = jsonTranscoder.stringToJsonObject(gson.toJson(user));
            final UserProfile userProfile = user.getUserProfile();
            final String userDocumentId = computeUserId(userProfile.getFirstName(), userProfile.getLastName());
            return JsonDocument.create(userDocumentId, userJsonObject);
        } catch (Exception e) {
            LOGGER.error("Error during json transformation", e);
            return null;
        }
    }

    String computeUserId(String firstName, String lastName) {
        return String.format("%s%s_%s", USER_DOCUMENT_PREFIX, firstName.toLowerCase(), lastName.toLowerCase());
    }
}
