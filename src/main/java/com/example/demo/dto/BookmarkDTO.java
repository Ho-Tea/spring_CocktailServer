package com.example.demo.dto;

import com.example.demo.model.BookmarkEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookmarkDTO {
	private String id;
	private String Bookmark;
	private boolean done;

	public BookmarkDTO(final BookmarkEntity entity) {
		this.id = entity.getId();
		this.Bookmark = entity.getBookmark();
		this.done = entity.isDone();
	}

	public static BookmarkEntity toEntity(final BookmarkDTO dto) {
		return BookmarkEntity.builder()
						.id(dto.getId())
						.Bookmark(dto.getBookmark())
						.done(dto.isDone())
						.build();

	}
}

