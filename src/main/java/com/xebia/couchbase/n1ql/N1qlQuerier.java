package com.xebia.couchbase.n1ql;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryRow;

import java.util.List;

import static com.couchbase.client.java.query.Select.selectDistinct;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class N1qlQuerier {

    public String N1QL_SERVER_ADDRESS = "couchbase-n1ql.aws.xebiatechevent.info";
    public CouchbaseEnvironment COUCHBASE_ENVIRONMENT = DefaultCouchbaseEnvironment.builder()
            .connectTimeout(10_000)
            .disconnectTimeout(10_000)
            .kvTimeout(10_000)
            .managementTimeout(10_000)
            .queryEnabled(true)
            .queryTimeout(20_000).build();

    public CouchbaseCluster couchbaseCluster =
            CouchbaseCluster.create(COUCHBASE_ENVIRONMENT, N1QL_SERVER_ADDRESS);

    public Bucket N1QL_PUBLICOTAURUS_BUCKET = couchbaseCluster.openBucket("publicotaurus");

    /**
     * Get the city with the more of people with specified initials
     *
     * @return city name
     */
    //TODO Exercise 11
    // Don't use the Configuration class, but the constants defined in this class.
    public List<String> getCityWithInhabitantsInitials(String firstNameBegin, String lastNameBegin) {
        return null;
    }

}
