/**
 * 
 */
package com.crossover.techtrial.controller;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.dto.TopMemberDTO;
import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.service.MemberService;

/**
 * 
 * @author crossover
 */

@RestController
public class MemberController {
  
  @Autowired
  MemberService memberService;

  @Autowired
  EntityManager em;
  /*
   * PLEASE DO NOT CHANGE SIGNATURE OR METHOD TYPE OF END POINTS
   */
  @PostMapping(path = "/api/member")
  public ResponseEntity<Member> register(@RequestBody Member p) {



      Member member = memberService.findByEmail(p.getEmail());
      if (member != null) {
        //email exists
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message", "Email Exists already");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

      }



    return ResponseEntity.ok(memberService.save(p));
  }
  
  /*
   * PLEASE DO NOT CHANGE API SIGNATURE OR METHOD TYPE OF END POINTS
   */
  @GetMapping(path = "/api/member")
  public ResponseEntity<List<Member>> getAll() {
    return ResponseEntity.ok(memberService.findAll());
  }
  
  /*
   * PLEASE DO NOT CHANGE API SIGNATURE OR METHOD TYPE OF END POINTS
   */
  @GetMapping(path = "/api/member/{member-id}")
  public ResponseEntity<Member> getMemberById(@PathVariable(name="member-id", required=true)Long memberId) {
    Member member = memberService.findById(memberId);
    if (member != null) {
      return ResponseEntity.ok(member);
    }
    return ResponseEntity.notFound().build();
  }
  
  
  /**
   * This API returns the top 5 members who issued the most books within the search duration. 
   * Only books that have dateOfIssue and dateOfReturn within the mentioned duration should be counted.
   * Any issued book where dateOfIssue or dateOfReturn is outside the search, should not be considered. 
   * 
   * DONT CHANGE METHOD SIGNATURE AND RETURN TYPES
   * @return
   */
  @GetMapping(path = "/api/member/top-member")
  public ResponseEntity<List<TopMemberDTO>> getTopMembers(
      @RequestParam(value="startTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
      @RequestParam(value="endTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime){
    List<TopMemberDTO> topMembers = new ArrayList<TopMemberDTO>();
    /**
     * Your Implementation Here. 
     * 
     */
    Query q = em.createNativeQuery(" select m.name, m.email, t.member_id memberId, count(book_id) bookCount\n" +
            " from crosslibrary.transaction t\n" +
            " left join crosslibrary.member m on t.member_id = m.id\n" +
            " where t.date_of_issue >='"+startTime+"' and t.date_of_issue <='"+endTime+"'\n" +
            " and t.date_of_return >='"+startTime+"' and t.date_of_return<='"+endTime+"'\n" +
            " group by book_id\n" +
            " order by bookCount desc\n" +
            " limit 5","TopMemberDTOMapping");

    topMembers = q.getResultList();


    return ResponseEntity.ok(topMembers);
    
  }
  
}
