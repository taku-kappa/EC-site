package backend.controller;

import backend.dto.response.ApiResponse;
import backend.dto.response.CategoryResponse;
import backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * カテゴリ関連API
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * カテゴリ一覧取得
     *
     * @return カテゴリ一覧
     */
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getCategories() {

        return ApiResponse.success(
                categoryService.getCategories()
        );
    }
}