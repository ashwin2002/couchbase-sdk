package org.ashwin.sample;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
//import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

public class Example {
	public static void main(String... args) throws Exception {

		Cluster cluster = CouchbaseCluster.create("10.111.182.101");
		cluster.authenticate("Administrator", "password");
		Bucket bucket = cluster.openBucket("default");

		JsonDocument doc = JsonDocument.create("id",JsonObject.create().put("name", "value"));
		bucket.insert(doc);		
	}
}

