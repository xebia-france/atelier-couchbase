package com.xebia.couchbase.user;

import com.google.gson.Gson;
import com.xebia.couchbase.batch.UserReaderFromCsv;
import com.xebia.couchbase.location.City;
import com.xebia.couchbase.location.Country;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static com.xebia.couchbase.Configuration.COUCHBASE_CLIENT;
import static com.xebia.couchbase.location.AddressBuilder.anAddress;
import static com.xebia.couchbase.user.UserBuilder.anUser;
import static com.xebia.couchbase.user.UserProfileBuilder.anUserProfile;
import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    private static UserRepository userRepository;
    private static Gson gson;

    @BeforeClass
    public static void staticSetUp() throws Exception {
        userRepository = new UserRepository();
        gson = new Gson();
    }

    @Test
    public void should_insert_user_in_database() throws Exception {
        final User user = anUser().withUserProfile(
                anUserProfile().withFirstName("Antoine").withLastName("Michaud").withSummary("Java Developer")
                        .withAddress(anAddress().withCity(new City("Paris", 1_000_000)).withCountry(new Country("France"))
                                .build()).build()).build();

        userRepository.insertUser(user);
        final User resultUser = findUser("user::v1::antoine_michaud");
        resultUser.getUserProfile().setSummary("Java Developer");
        assertThat(resultUser).isEqualTo(user);
    }

    @Test
    public void should_update_with_an_optimistic_lock() throws Exception {
        final String userKey = "user::v1::antoine_michaud";
        final UserWithCas user1 = userRepository.findUserWithCas(userKey);
        final UserWithCas user2 = userRepository.findUserWithCas(userKey);
        user1.getUser().getUserProfile().setSummary("Couchbase Developer");
        user2.getUser().getUserProfile().setSummary("PHP Developer");

        userRepository.updateUser(userKey, user1.getCasId(), user1.getUser());
        userRepository.updateUser(userKey, user1.getCasId(), user2.getUser());

        final User resultUser = findUser(userKey);
        assertThat(resultUser.getUserProfile().getSummary()).isEqualTo("Couchbase Developer");
    }

    @Test
    public void should_count_number_of_document_retrieval() throws Exception {
        // Given
        final Object userDocumentRetrievalCountOrNull = COUCHBASE_CLIENT
                .get("user_document_retrieval_count");

        Long userDocumentRetrievalCount = userDocumentRetrievalCountOrNull != null
                ? parseLong(userDocumentRetrievalCountOrNull.toString())
                : -1L;

        // When
        userRepository.findUser("antoine_michaud");

        // Then
        final Long eventualUserDocumentRetrievalCount = parseLong(COUCHBASE_CLIENT.get("user_document_retrieval_count").toString());
        assertThat(eventualUserDocumentRetrievalCount).isEqualTo(userDocumentRetrievalCount + 1);
    }

    @Test
    public void should_not_allow_read_during_edition() throws Exception {
        //TODO lancer ce test en debug pour laisse le temps à couchbase de bien s'initialiser
        //FIXME trouver une alternative pour laisser le temps à couchbase de se charger
        assertThat(userRepository.getAndLock("antoine_michaud")).isNotNull();
        assertThat(userRepository.getAndLock("antoine_michaud")).isNull();
    }

    @Test
    public void should_insert_a_bulk_of_users() throws Exception {
        final Collection<User> users = UserReaderFromCsv.getUsersFrom("users.csv");

        for (User user : users) {
            userRepository.insertUser(user);
        }
    }

    private User findUser(String id) throws java.io.IOException {
        return gson.getAdapter(User.class).fromJson(
                findDocument(id).toString());
    }

    private Object findDocument(String id) {
        return COUCHBASE_CLIENT.get(id);
    }

}