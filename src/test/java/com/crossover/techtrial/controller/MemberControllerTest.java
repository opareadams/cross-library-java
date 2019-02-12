/**
 * 
 */
package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.repositories.MemberRepository;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private MemberController memberController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  MemberRepository memberRepository;
  
  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
  }
  
  @Test
  public void testMemberRegsitrationsuccessful() throws Exception {
    HttpEntity<Object> member = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
            + " \"membershipStatus\": \"ACTIVE\",\"membershipStartDate\":\"2018-08-08T12:12:12\" }");
    
    ResponseEntity<Member> response = template.postForEntity(
        "/api/member", member, Member.class);
    
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
    
    //cleanup the user
   // memberRepository.deleteById(response.getBody().getId());
  }

  @Test
  public void testMemberRegsitrationBlockDuplicateEmail() throws Exception {
    HttpEntity<Object> member = getHttpEntity(
            "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\","
                    + " \"membershipStatus\": \"ACTIVE\",\"membershipStartDate\":\"2018-08-08T12:12:12\" }");

    ResponseEntity<Member> response = template.postForEntity(
            "/api/member", member, Member.class);

    Assert.assertEquals(400,response.getStatusCode().value());

    //cleanup the user
    // memberRepository.deleteById(response.getBody().getId());
  }

  @Test
  public void testGetAll(){
    HttpEntity<String> entity = template.getForEntity("/api/member",String.class);

    ResponseEntity<String> response =template.getForEntity("/api/member", String.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(200,response.getStatusCode().value());

  }

  @Test
  public void testGetMemberById(){
    HttpEntity<String> entity = template.getForEntity("/api/member/12",String.class);

    ResponseEntity<String> response =template.getForEntity("/api/member/12", String.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(200,response.getStatusCode().value());

  }

  @Test
  public void testGetTopMembers(){
    HttpEntity<String> entity = template.getForEntity("/api/member/top-member?startTime=2019-01-19T00:00:00&endTime=2019-01-20T23:59:59",String.class);

    ResponseEntity<String> response =template.getForEntity("/api/member/top-member?startTime=2019-01-19T00:00:00&endTime=2019-01-20T23:59:59", String.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(200,response.getStatusCode().value());

  }




  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
