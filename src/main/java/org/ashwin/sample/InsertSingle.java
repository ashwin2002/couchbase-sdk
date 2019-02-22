package org.ashwin.sample;

import java.util.UUID;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class InsertSingle {
	public static void main(String[] args) {

		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().connectTimeout(10000).kvTimeout(3000).build();
		Cluster cluster = CouchbaseCluster.create(env, "10.111.181.101");
		cluster.authenticate("Administrator", "password");

		// Connect to default bucket
		final Bucket bucket = cluster.openBucket("default");

		// Perform document insert
		JsonObject content = JsonObject.empty().put("name", "John Doe").put("type", "Person")
				.put("email", "john.doe@mydomain.com").put("homeTown", "Chicago");

		String id = UUID.randomUUID().toString();
		JsonDocument document = JsonDocument.create(id, content);
		JsonDocument inserted = bucket.insert(document);
		System.out.println(inserted);

		// Perform document get
		JsonDocument retrieved = bucket.get(id);
		System.out.println(retrieved);
	}
}
