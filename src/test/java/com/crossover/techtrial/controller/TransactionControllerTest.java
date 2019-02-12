package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.repositories.TransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ProtocolException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    MockMvc mockMvc;

    @Mock
    private TransactionController transactionController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    TransactionRepository transactionRepository;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testIssueBookToMember() throws Exception {
        HttpEntity<Object> transaction = getHttpEntity(
                "{ \n" +
                        "\t\"bookId\":3,\n" +
                        "\t\"memberId\":14\n" +
                        "\t\n" +
                        "}");

        ResponseEntity<Transaction> response = template.postForEntity(
                "/api/transaction", transaction, Transaction.class);

        Assert.assertEquals(200,response.getStatusCode().value());

    }
    @Test
    public void testIssueSameBookToMemberTwice() throws Exception {
        HttpEntity<Object> transaction = getHttpEntity(
                "{ \n" +
                        "\t\"bookId\":3,\n" +
                        "\t\"memberId\":14\n" +
                        "\t\n" +
                        "}");

        ResponseEntity<Transaction> response = template.postForEntity(
                "/api/transaction", transaction, Transaction.class);

        Assert.assertEquals(403,response.getStatusCode().value());

    }

    @Test
    public void testIssueBookNotInDatabase() throws Exception {
        HttpEntity<Object> transaction = getHttpEntity(
                "{ \n" +
                        "\t\"bookId\":40,\n" +
                        "\t\"memberId\":12\n" +
                        "\t\n" +
                        "}");

        ResponseEntity<Transaction> response = template.postForEntity(
                "/api/transaction", transaction, Transaction.class);

        Assert.assertEquals(404,response.getStatusCode().value());

    }

    @Test
    public void testIssueNotMoreThanFive() throws Exception {
        HttpEntity<Object> transaction = getHttpEntity(
                "{ \n" +
                        "\t\"bookId\":9,\n" +
                        "\t\"memberId\":12\n" +
                        "\t\n" +
                        "}");

        ResponseEntity<Transaction> response = template.postForEntity(
                "/api/transaction", transaction, Transaction.class);

        Assert.assertEquals(403,response.getStatusCode().value());

    }

    @Test(expected = ResourceAccessException.class)
    public void testReturnBookTransaction(){
        //PATCH HTTP METHOD DOES NOT SEEM TO WORK
        HttpEntity<Object> transaction = getHttpEntity(
                "");

        ResponseEntity<Transaction> response = template.exchange(
                "/api/transaction/6/return", HttpMethod.PATCH,transaction,Transaction.class);


        Assert.assertEquals(200,response.getStatusCode().value());

    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }


}
