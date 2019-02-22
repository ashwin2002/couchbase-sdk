package org.ashwin.sample;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.*;
import com.couchbase.client.java.analytics.AnalyticsParams;
import com.couchbase.client.java.analytics.AnalyticsQuery;
import com.couchbase.client.java.analytics.AnalyticsQueryResult;
import com.couchbase.client.java.analytics.AnalyticsQueryRow;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class CbasClientServerTimeout {

	public static void main(String[] args) throws InterruptedException {

		CouchbaseEnvironment env  = DefaultCouchbaseEnvironment.builder().mutationTokensEnabled(true).computationPoolSize(5)
				.maxRequestLifetime(TimeUnit.SECONDS.toMillis(300)).socketConnectTimeout(100000).connectTimeout(100000)
				.build();
		CouchbaseCluster cluster = CouchbaseCluster.create(env, "10.111.181.101");
		cluster.authenticate("Administrator", "password");
		Bucket bucket = cluster.openBucket("travel-sample");

		AnalyticsParams params = AnalyticsParams.build();
		params.rawParam("timeout", "60s");
		params.withContextId("xyz");

		AnalyticsQueryResult result = bucket.query(
				AnalyticsQuery.simple("SELECT sleep(count(*), 300000) FROM travel_ds", params), 5, TimeUnit.SECONDS);
		for (AnalyticsQueryRow row : result) {
			System.out.println(row.toString());
		}
	}
}