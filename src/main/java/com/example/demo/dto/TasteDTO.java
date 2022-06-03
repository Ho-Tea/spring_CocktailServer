package com.example.demo.dto;


import com.example.demo.model.TasteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TasteDTO {
    private String id;
    private String Taste;
    private boolean done;

    public TasteDTO(final TasteEntity entity) {
        this.id = entity.getId();
        this.Taste = entity.getTaste();
        this.done = entity.isDone();
    }

    public static TasteEntity toEntity(final TasteDTO dto) {
        return TasteEntity.builder()
                .id(dto.getId())
                .Taste(dto.getTaste())
                .done(dto.isDone())
                .build();

    }
}
