package com.example.SoftwareProject.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {     //DB갑에 접근하는 형식
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; //이 오브젝트의 id - 키값
    private String userId; //이 오브젝트를 생성한 사용자의 아이디
    private String title; //예 운동하기
    private boolean done;
}
