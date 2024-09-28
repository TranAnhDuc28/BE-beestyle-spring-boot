package com.datn.beestyle.service.category;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.category.CategoryResponse;
import com.datn.beestyle.dto.category.CreateCategoryRequest;
import com.datn.beestyle.dto.category.UpdateCategoryRequest;
import com.datn.beestyle.dto.category.UserCategoryResponse;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.mapper.CategoryMapper;
import com.datn.beestyle.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CategoryService
        extends GenericServiceAbstract<Category, Integer, CreateCategoryRequest, UpdateCategoryRequest, CategoryResponse>
        implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(IGenericRepository<Category, Integer> entityRepository,
                           IGenericMapper<Category, CreateCategoryRequest, UpdateCategoryRequest, CategoryResponse> mapper,
                           EntityManager entityManager, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        super(entityRepository, mapper, entityManager);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<UserCategoryResponse> getAllForUser() {
        List<Object[]> results = categoryRepository.findAllForUser();
        Map<Integer, UserCategoryResponse> userCategoryResponseMap = new HashMap<>();
        List<UserCategoryResponse> rootCategories = new ArrayList<>();

        for (Object[] row : results) {
            Integer id = (Integer) row[0];
            String categoryName = (String) row[1];
            String slug = (String) row[2];
            Integer parentId = (Integer) row[3];

            UserCategoryResponse userCategoryResponse = new UserCategoryResponse(id, categoryName, slug);
            userCategoryResponseMap.put(id, userCategoryResponse);

            if (parentId != null) {
                UserCategoryResponse parentCategory = userCategoryResponseMap.get(parentId);
                if (parentCategory != null) {
                    parentCategory.getCategoryChildren().add(userCategoryResponse);
                }
            } else {
                rootCategories.add(userCategoryResponse);
            }
        }
        return rootCategories;
    }

    @Override
    protected List<CreateCategoryRequest> beforeCreateEntities(List<CreateCategoryRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateCategoryRequest> beforeUpdateEntities(List<UpdateCategoryRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateCategoryRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateCategoryRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateCategoryRequest request, Category entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateCategoryRequest request, Category entity) {

    }

    @Override
    protected String getEntityName() {
        return "Category";
    }


}
