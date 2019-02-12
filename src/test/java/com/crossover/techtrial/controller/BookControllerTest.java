package com.crossover.techtrial.controller;



import com.crossover.techtrial.model.Book;
import com.crossover.techtrial.model.BookTest;
import com.crossover.techtrial.repositories.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    MockMvc mockMvc;

    @Mock
    private BookController bookController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    BookRepository bookRepository;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetBooks(){
        HttpEntity<String> entity = template.getForEntity("/api/book",String.class);

        ResponseEntity<String> response =template.getForEntity("/api/book", String.class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(200,response.getStatusCode().value());

    }

    @Test
    public void testSaveBook(){
        HttpEntity<Object> book = getHttpEntity(
                "{\n" +
                        "\t\"title\":\"The Quest\"\n" +
                        "}");

        ResponseEntity<Book> response = template.postForEntity(
                "/api/book", book, Book.class);

        Assert.assertEquals("The Quest", response.getBody().getTitle());
        Assert.assertEquals(200,response.getStatusCode().value());


    }


    @Test
    public void testGetBookById(){
        HttpEntity<String> entity = template.getForEntity("/api/book/1",String.class);

        ResponseEntity<String> response =template.getForEntity("/api/book/1", String.class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(200,response.getStatusCode().value());

    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }

}
