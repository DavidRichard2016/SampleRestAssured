/**
 * Created by xbinli on 17/01/2017.
 * i use tool RestAssured  to do rest api testing . This sample contain 4 tests and using get , post ,put ,delete methods.
 */
import io.restassured.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
//import static org.hamcrest.Matchers.hasSize;

//junit
import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {
    private final String host = "http://127.0.0.1";
    private final int port = 3000;
    private final String path = "/books";

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = host;
        RestAssured.port = port;
        RestAssured.basePath = path;
        System.out.print("New base host is:" + RestAssured.baseURI);

    }
    @After
    public void tearDown() throws Exception {
        RestAssured.baseURI = null;
        RestAssured.port = 80;
        RestAssured.basePath = null;

    }


    /** responses :[
     {
     "id": 1,
     "title": "json-server",
     "author": "david",
     "price": "35.00"
     },
     {
     "id": 2,
     "title": "How to do QA job during your first project",
     "author": "jane",
     "price": "24.00"
     },
     {
     "id": 3,
     "title": "Could you get back your mnoney?",
     "author": "wilson",
     "price": "49.00"
     }
     ]
     *
     */

    @Test
    public void testGetBooks() {
        given().
                log().
                method().
        when().
                get().

        then().
                statusCode(200).
                body("$",hasSize(3)).
                body("[0].author",equalTo("david")).
                time(lessThan(2000L));

    }

    @Test
    public void testGetBook() {
        given().
                pathParam("bookId","1").
        when().
                get("/{bookId}").
        then().
                log().body().
                statusCode(200).
                body("author",equalTo("david")).
                body("id",equalTo(1));

    }

    /**
     * use post method
     */

    @Test
    public void testAddBook() {
        final String newBook = "{\"id\":4,\"title\":\"new book\",\"author\":\"new author\",\"price\":\"35.00\"}";
        given().
                log().all().
                body(newBook).
                contentType("application/json"). //set the Content-Type, A POST, PUT or PATCH request should include a Content-Type: application/json header to use the JSON in the request body.
        when().
                post().
        then().
                log().body().
                statusCode(201).  // 201 stands for new item
                body("id",equalTo(4));
    }

    /**
     * use put method
     *
     */
    @Test
    public void testUpdateBook() {
        final String newAuthor = "richard";
        final String updateBody = "{\"author\":newAuthor}";
        given().
                pathParam("bookId",2).
                contentType("application/json").
        when().
                put("/{bookId}").
        then().
                statusCode(200).
                body("author",equalTo(newAuthor));

    }

    /**
     * use delete method
     */

    @Test
    public void testDeleteBook(){
        given().
                pathParam("bookId",3).
        when().
                delete("/{bookId}").
        then().
                log().all().
                statusCode(200);

    }

    @Test
    @Ignore("not implemented")
    public void testcreateBook() {
        fail("not implemented");

    }
}
