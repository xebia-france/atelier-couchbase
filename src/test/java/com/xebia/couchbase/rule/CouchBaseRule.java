package com.xebia.couchbase.rule;

import org.junit.rules.TestRule;

public class CouchBaseRule {

//    private final Bucket publicotaurusBucket;
//    private final BucketManager bucketManager;
//
//    public CouchBaseRule(String ip) {
//        publicotaurusBucket = cluster(ip).openBucket(Configuration.PUBLICOTAURUS_BUCKET_NAME);
//        bucketManager = publicotaurusBucket.bucketManager();
//    }
//
//    private CouchbaseCluster cluster(String ip) {
//        return CouchbaseCluster.create(Configuration.COUCHBASE_ENVIRONMENT, Configuration.COUCHBASE_SERVER_ADDRESS);
//    }
//
//    public void create(JsonDocument jsonDocument) {
//        publicotaurusBucket.upsert(jsonDocument);
//    }
//
//    public Object find(String key) {
//        return publicotaurusBucket.get(key);
//    }
//
//    public Statement apply(final Statement base, Description description) {
//        return new Statement() {
//            @Override
//            public void evaluate() throws Throwable {
//
//                try {
//                    base.evaluate();
//                } finally {
//                    bucketManager.flush();
//                }
//            }
//        };
//    }


}
