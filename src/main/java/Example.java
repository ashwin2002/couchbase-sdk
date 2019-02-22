import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
//import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.subdoc.MutateInBuilder;
import com.couchbase.client.java.subdoc.SubdocOptionsBuilder;

public class Example {
	public static void main(String... args) throws Exception {

		Cluster cluster = CouchbaseCluster.create("10.112.183.101");
		cluster.authenticate("Administrator", "password");
		Bucket bucket = cluster.openBucket("default");

		JsonDocument doc = JsonDocument.create("id",JsonObject.create().put("name", "value"));
		bucket.insert(doc);
		
		MutateInBuilder mutateIn = bucket.mutateIn("id");
		SubdocOptionsBuilder xattr = new SubdocOptionsBuilder().createPath(true).xattr(true);
		mutateIn.upsert("a", "{'value': 1}", xattr).execute();
		xattr = new SubdocOptionsBuilder().xattr(true);
		mutateIn.remove("a", xattr).execute();
	}
}

/*
 * String content = "{\"hello\": \"couchbase\", \"active\": true}";
 * bucket.upsert(RawJsonDocument.create("1", content));
 * 
 * bucket.mutateIn("1").insert("a", "{'value': 1}", new
 * SubdocOptionsBuilder().createParents(true).xattr(true)).execute();
 * System.out.println("Check ..."); bucket.mutateIn("1").upsert("a",
 * "{'value': 1111}", new
 * SubdocOptionsBuilder().createParents(true).xattr(true)).execute();
 * 
 * 
 * 
 * JsonObject.create()
 * 
 * /* //bucket.upsert(JsonStringDocument.create("1", "{\"name\":\"alex\"}"));
 * //System.out.println((bucket.get("1", RawJsonDocument.class)));
 * 
 * //bucket.upsert(JsonStringDocument.create("s", "{\"name\":\"alex\"}"));
 * //System.out.println(bucket.get("s", JsonStringDocument.class));
 * 
 * //bucket.upsert(StringDocument.create("stringDoc", "Hello World"));
 * //System.out.println(bucket.get("stringDoc", StringDocument.class));
 * 
 * ByteBuf toWrite = Unpooled.copiedBuffer("{\"name\":\"alex\"}",
 * CharsetUtil.UTF_8);
 * 
 * // Write it bucket.upsert(BinaryDocument.create("s", toWrite));
 * 
 * 
 * bucket.mutateIn("s").upsert("click", "Hello World1234", new
 * SubdocOptionsBuilder().createParents(true).xattr(true));
 * 
 * // Create buffer out of a string //ByteBuf toWrite =
 * Unpooled.copiedBuffer("{\"name\":\"alex\"}", CharsetUtil.UTF_8);
 * 
 * // Write it bucket.upsert(BinaryDocument.create("binaryDoc", toWrite));
 * 
 * // Read it back BinaryDocument read = bucket.get("binaryDoc",
 * BinaryDocument.class);
 * 
 * // Print it System.out.println(read.content().toString(CharsetUtil.UTF_8));
 * 
 * // Free the resources ReferenceCountUtil.release(read.content());
 * 
 * // Create a JSON Document JsonObject arthur = JsonObject.create().put("name",
 * "Arthur").put("email", "kingarthur@couchbase.com") .put("interests",
 * JsonArray.from("Holy Grail", "African Swallows"));
 * 
 * // Store the Document bucket.upsert(JsonDocument.create("u:king_arthur",
 * arthur));
 * 
 * // Load the Document and print it // Prints Content and Metadata of the
 * stored Document System.out.println(bucket.get("u:king_arthur"));
 * 
 * // Create a N1QL Primary Index (but ignore if it exists)
 * bucket.bucketManager().createN1qlPrimaryIndex(true, false);
 * 
 * // Perform a N1QL Query N1qlQueryResult result = bucket.query(N1qlQuery.
 * parameterized("SELECT name FROM default WHERE $1 IN interests",
 * JsonArray.from("African Swallows")));
 * 
 * // Print each found Row for (N1qlQueryRow row : result) { // Prints
 * {"name":"Arthur"} System.out.println(row); }
 * 
 * }}
 */
