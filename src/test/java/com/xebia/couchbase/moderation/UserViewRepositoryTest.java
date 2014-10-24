package com.xebia.couchbase.moderation;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserViewRepositoryTest {
    @Test
    public void should_retrieve_user_from_moderator_point_of_view() throws Exception {
        // Given
        // When
        final ViewResult inactiveUsers = UserView.getInactiveUsers();

        // Then
        final ViewRow userViewRow = inactiveUsers.allRows().get(0);
        assertThat(userViewRow).isNotNull();
        assertThat(userViewRow.key()).isEqualTo("VINCENT");

        Map<String, Object> expectedJsonMap = ImmutableMap.<String, Object>builder()
                .put("userId", "user::adrian_vincent")
                .put("userName", "ADRIAN VINCENT")
                .put("cityName", "San Diego")
                .put("active", false)
                .build();
        assertThat(((JsonObject)userViewRow.value()).toMap()).isEqualTo(expectedJsonMap);
    }

    @Test
    //Exercice 10
    public void should_paginate() throws Exception {

    }

    //    public void disableAnUser(String userId) throws IOException {
//        final UserWithCas userWithCas = userRepository.findUserWithCas(userId);
//        userWithCas.getUser().setActive(false);
//        userRepository.updateUser(userId, userWithCas.getCasId(), userWithCas.getUser());
//    }

//    @Test
//    public void should_retrieve_most_present_cities() throws Exception {
//        // Given
//
//        // When
//        Configuration.publicotaurusBucket()
//                .async()
//                .query(
//                        select("*").from(Configuration.PUBLICOTAURUS_BUCKET_NAME).where(x("active").is(x(false))))
//        .doOnNext(result -> {
//            if (!result.success()) {
//                System.err.println(result.error());
//            }
//        });
//
//        // Then
//
//    }


}