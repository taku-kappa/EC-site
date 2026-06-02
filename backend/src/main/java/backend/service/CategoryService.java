package backend.service;

import backend.dto.response.CategoryResponse;
import backend.entity.Category;
import backend.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリ関連サービス
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * カテゴリ一覧取得
     *
     * @return カテゴリ一覧
     */
    public List<CategoryResponse> getCategories() {

        List<Category> categories = categoryMapper.findAll();

        return categories.stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName()
                ))
                .toList();
    }
}