/**
 * 
 */
package com.crossover.techtrial.dto;

import javax.persistence.*;

/**
 * @author crossover
 *
 */
@Entity
@SqlResultSetMapping(
        name="TopMemberDTOMapping",
        classes={
                @ConstructorResult(
                        targetClass = TopMemberDTO.class,

                        columns={

                                @ColumnResult(name="name",type = String.class),
                                @ColumnResult(name="email",type =  String.class),
                                @ColumnResult(name="memberId",type = Long.class),
                                @ColumnResult(name="bookCount",type = Integer.class),


                        }
                )
        }
)
public class TopMemberDTO {
  
  /**
   * Constructor for TopMemberDTO
   * @param memberId
   * @param name
   * @param email
   * @param bookCount
   */
  public TopMemberDTO(
      String name, 
      String email,
      Long memberId,
      Integer bookCount) {
    this.name = name;
    this.email = email;
    this.memberId = memberId;
    this.bookCount = bookCount;
  }
  
  public TopMemberDTO() {
    
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private Long memberId;
  
  private String name;
  
  private String email;
  
  private Integer bookCount;

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getBookCount() {
    return bookCount;
  }

  public void setBookCount(Integer bookCount) {
    this.bookCount = bookCount;
  }
}
