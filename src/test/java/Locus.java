import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

public class Locus {

    /*Private variables*/
    private final String BASE_URL = "https://the-one-api.dev/v2";
    private final String BEARER_TOKEN = "4qAaXynbVomwqHwO6MXW";

    //Get books data - hit API endpoint https://the-one-api.dev/v2/book . It should return 3
    //books.
    @Test
    public void testGetBooksData() {
        Response response = RestAssured.get(BASE_URL + "/book");
        //content type
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
        //status code
        Assert.assertEquals(response.getStatusCode(), 200);
        //size
        Assert.assertEquals(response.jsonPath().getList("docs").size(), 3);
        //Ids should not be duplicate
        List<String> bookIds = response.jsonPath().getList("docs._id");
        Assert.assertEquals(bookIds.size(), new HashSet<>(bookIds).size());
    }

    /*Negative case for getting movies - hit API endpoint https://the-one-api.dev/v2/movie.
    Check for the negative case.*/
    @Test
    public void testNegativeCaseForGettingMovies() {
        Response response = RestAssured.get(BASE_URL + "/movie");
        //content type
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
        //error status
        Assert.assertEquals(response.getStatusCode(), 401);
        //error message
        Assert.assertTrue(response.jsonPath().getString("message").equals("Unauthorized."));
    }

    /*Positive case for getting movies - hit same endpoint as above but with Bearer token
    4qAaXynbVomwqHwO6MXW . Assert on the correct response.*/
    @Test
    public void testPositiveCaseForGettingMovies() {
        Response response = RestAssured.given().header("Authorization", "Bearer " + BEARER_TOKEN)
                .get(BASE_URL + "/movie");
        //content type
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
       //status code
        Assert.assertEquals(response.getStatusCode(), 200);
        //size of response
        Assert.assertTrue(response.jsonPath().getList("docs").size() > 0);
        //Id should be type of String
        Assert.assertTrue(response.jsonPath().getString("docs[0]._id") instanceof String);

    }

    /*Two step case - get the list of movies from the above case &amp; now you need to get the
    quote of a movie from the url https://the-one-api.dev/v2/movie/{id}/quote where id is the id of
    the one the movies returned from the above url. Assert for the usecase working fine.*/
    @Test
    public void testTwoStepCaseGetMovieQuote() {
        try {
            // Get the list of movies from api
            Response moviesResponse = RestAssured.given().header("Authorization", "Bearer " + BEARER_TOKEN)
                    .get(BASE_URL + "/movie");
            //status code
            Assert.assertEquals(moviesResponse.getStatusCode(), 200);
            //get first movie id
            String movieId = moviesResponse.jsonPath().getString("docs[0]._id");
            //Id should not be null
            Assert.assertNotNull(movieId, "Movie ID is not received");

            // Get the quote of a movie
            Response quoteResponse = RestAssured.given().header("Authorization", "Bearer " + BEARER_TOKEN)
                    .get(BASE_URL + "/movie/" + movieId + "/quote");
            Assert.assertEquals(quoteResponse.getStatusCode(), 200);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
