import static org.junit.Assert.fail;
//static import rest assured
import io.restassured.RestAssured;
import io.restassured.*;
import static io.restassured.RestAssured.*;
import static  io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

// import junit method
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestDouban {
    private String bookId = "/26933142";

    @Before
    public void setUp() throws Exception {
//		set the url and port number
        RestAssured.baseURI = "https://api.douban.com/v2/book"; // set the douban as the default host
//      RestAssured.port = 80;
        String newUri = RestAssured.baseURI ;
        System.out.println(newUri);
    }

    @After
    public void tearDown() throws Exception {
    }
    //   sample   target   https://api.douban.com/v2/book/26933142

    @Test
    public void testGetBook() {
        String bookPath = "/26933142";
        get(bookPath)
                .then()
                .body("id",equalTo("26933142"));

    }


    @Test
    public void testJsonSchemaValidation() {
        String bookPath = "/26933142";
        get("/26933142")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("bookResonseSchema.json"));

    }

    @Test
    public void testStatus_200() {
        get(bookId)
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void testHeader() {
        get(bookId)
                .then()
                .assertThat()
                .header("Keep-Alive","timeout=30");
    }

    @Test
    public void testHeaders() {
        get(bookId)
                .then()
                .assertThat()
                .headers("X-Ratelimit-Limit2","100","server",containsString("dae"));
    }

    @Test
    public void testContentType_JSON() {
        get(bookId)
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    @Test
    public void  testgetResponse(){
        String json = get(bookId).asString();
        System.out.println(json);

    }

    @Test
    public void testResponseTime() {
        long timeInMs = get(bookId).time();
        System.out.println("response time (time in MS) is: " + timeInMs);
    }

    @Test
    public void testResponseTimeLess200() {
        when().
                get(bookId).
                then().
                time(lessThan(2000L)); // Milliseconds
    }

//    @Test
//    public void testBody(){
//        fail("Not yet implemented");
//    }

}
