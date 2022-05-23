package com.example.SoftwareProject.controller;

import com.example.SoftwareProject.dto.ResponseDTO;
import com.example.SoftwareProject.dto.TodoDTO;
import com.example.SoftwareProject.model.TodoEntity;
import com.example.SoftwareProject.service.TodoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service; //TodoService DI

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }



    @PostMapping
    public ResponseEntity<?> createTodo(
        @AuthenticationPrincipal String userId,
        @RequestBody TodoDTO dto)
    {
        try{

            // 1. TodoEntity로 변환한다
            TodoEntity entity = TodoDTO.toEntity(dto);

            // 2. id를 null로 초기화 한다 생성 당시에는 id가 없어야 하기 때문에
            entity.setId(null);

            // 3.사용자 아이디를 설정
            entity.setUserId(userId);

            // 4. 서비스를 이용해 Todo엔티티를 생성한다
            List<TodoEntity> entities = service.create(entity);

            // 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 6. 변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화 한다
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch(Exception e){
            //혹시 에러가 있는경우 dto대신 error에 메시지를 넣어 리턴한다
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){

        //1. 서비스 메서드의 retrieve()메서드를 사용해 Todo리스트를 가져온다
        List<TodoEntity> entities = service.retrieve(userId);

        //2. 자바스트림을 이용해 리턴된 엔티티리스트를 Tododto리스트로 변환한다
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //3. 변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //4. ResponseDTO를 리턴한다
        return ResponseEntity.ok().body(response);
    }


    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){

        //dto를 entity로 변환한다
        TodoEntity entity = TodoDTO.toEntity(dto);

        //id를 temporaryUserId로 초기화 한다. 여기는 4장 인증과 인가에서 수정할 예정
        entity.setUserId(userId);

        //서비스를 이용해 entity를 업데이트한다
        List<TodoEntity> entities = service.update(entity);

        //자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화 한다
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //ResponseDTO를 리턴한다
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            //TodoEntity로 변환한다
            TodoEntity entity = TodoDTO.toEntity(dto);

            //임시 사용자 아이디를 설정해 준다 이부분은 인증과 인가에서 수정할 예정이다
            //지금은 인증과 인가기능이 없으므로 한 사용자(temporary-user)만 로그인 없이 사용할 수 있는 애플리케이션인 셈이다
            entity.setUserId(userId);

            //서비스를 이용해서 entity를 삭제
            List<TodoEntity> entities = service.delete(entity);

            //자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화 한다
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //Response DTO를 리턴
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //혹시 예외가 있는경우 dto대신 error에 메시지를 넣어 리턴한다
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
