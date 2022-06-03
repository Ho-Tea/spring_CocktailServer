package com.example.demo.service;


import com.example.demo.model.TasteEntity;
import com.example.demo.persistence.TasteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TasteService {

    @Autowired
    private TasteRepository repository;
    public List<TasteEntity> create(final TasteEntity entity) {
        // Validations
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());
        return repository.findByUserId(entity.getUserId());
    }

    private void validate(final TasteEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<TasteEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TasteEntity> update(final TasteEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다. 이 메서드는 2.3.1 Create Taste에서 구현했다.
        validate(entity);

        // (2) 넘겨받은 엔티티 id를 이용해 TasteEntity를 가져온다. 존재하지 않는 엔티티는 업데이트 할 수 없기 때문이다.
        final Optional<TasteEntity> original = repository.findById(entity.getId());



        original.ifPresent(todo -> {
            // (3) 반환된 TasteEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
            todo.setTaste(entity.getTaste());
            todo.setDone(entity.isDone());

            // (4) 데이터베이스에 새 값을 저장한다.
            repository.save(todo);
        });

        // 2.3.2 Retrieve Taste에서 만든 메서드를 이용해 유저의 모든 Taste 리스트를 리턴한다.
        return retrieve(entity.getUserId());
    }


    public List<TasteEntity> delete(final TasteEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다. 이 메서드는 2.3.1 Create Taste에서 구현했다.
        validate(entity);

        try {
            // (2) 엔티티를 삭제한다.
            repository.delete(entity);
        } catch(Exception e) {
            // (3) exception 발생시 id와 exception을 로깅한다.
            log.error("error deleting entity ", entity.getId(), e);

            // (4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        // (5) 새 Taste리스트를 가져와 리턴한다.
        return retrieve(entity.getUserId());
    }
}
