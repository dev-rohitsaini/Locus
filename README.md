Test Script Folder Path

src->test->java->PicRightApiAssignment.java

Here are the assertions have been implemented the code:

testGetBooksData:

Asserts that the content type of the response is "application/json; charset=utf-8".
Asserts that the status code of the response is 200.
Asserts that the size of the list of books ("docs") in the response is 3.
Asserts that the IDs of books in the response are not duplicated.


testNegativeCaseForGettingMovies:

Asserts that the content type of the response is "application/json; charset=utf-8".
Asserts that the status code of the response is 401.
Asserts that the message in the response body is "Unauthorized.".


testPositiveCaseForGettingMovies:

Asserts that the content type of the response is "application/json; charset=utf-8".
Asserts that the status code of the response is 200.
Asserts that the size of the list of movies ("docs") in the response is greater than 0.
Asserts that the type of movie ID ("docs[0]._id") is String.


testTwoStepCaseGetMovieQuote:

Asserts that the status code of the movies response is 200.
Asserts that the movie ID obtained from the response is not null.
Asserts that the status code of the quote response is 200.
