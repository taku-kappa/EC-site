package backend.mapper;

import backend.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * カテゴリ関連Mapper
 */
@Mapper
public interface CategoryMapper {

    /**
     * カテゴリ一覧取得
     *
     * @return カテゴリ一覧
     */
    List<Category> findAll();
}