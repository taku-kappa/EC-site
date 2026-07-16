package backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * カテゴリテーブル Entity
 */
@Data
public class Category {

    // カテゴリID
    private Long id;

    // カテゴリ名
    private String name;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;
}