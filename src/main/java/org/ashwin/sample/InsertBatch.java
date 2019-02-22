package org.ashwin.sample;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import rx.Observable;
import rx.functions.Func1;

public class InsertBatch {
	public static void main(String[] args) {

		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().connectTimeout(10000).kvTimeout(3000).build();
		Cluster cluster = CouchbaseCluster.create(env, "10.111.181.101");
		cluster.authenticate("Administrator", "password");

		// Connect to default bucket
		final Bucket bucket = cluster.openBucket("default");

		// Generate a number of dummy JSON documents
		int docsToCreate = 10000;
		List<JsonDocument> documents = new ArrayList<JsonDocument>();
		for (int i = 0; i < docsToCreate; i++) {
			JsonObject content = JsonObject.create().put("counter", i).put("name", "Foo Bar");
			documents.add(JsonDocument.create("doc-" + i, content));
		}

		// Insert them in one batch, waiting until the last one is done.
		Observable.from(documents).flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
			public Observable<JsonDocument> call(final JsonDocument docToInsert) {
				return bucket.async().insert(docToInsert);
			}
		}).last().toBlocking().single();
	}
}
