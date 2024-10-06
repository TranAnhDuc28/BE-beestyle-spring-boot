package com.datn.beestyle.service.category;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.category.CategoryResponse;
import com.datn.beestyle.dto.category.CreateCategoryRequest;
import com.datn.beestyle.dto.category.UpdateCategoryRequest;
import com.datn.beestyle.dto.category.UserCategoryResponse;
import com.datn.beestyle.entity.BaseEntity;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.mapper.CategoryMapper;
import com.datn.beestyle.repository.CategoryRepository;
import com.datn.beestyle.util.AppUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    public PageResponse<List<CategoryResponse>> getAllForAdmin(Pageable pageable, String name, String status) {
        Map<Integer, String> categoryNames;
        List<Integer> ids;
        List<CategoryResponse> categoryResponses;
        Page<Category> categoryPages;

        if (!StringUtils.hasText(name) && status == null) {
            categoryPages = categoryRepository.findAll(pageable);
        } else {
            Integer statusValue = null;
            if (StringUtils.hasText(status)) statusValue = Status.valueOf(status.toUpperCase()).getValue();
            categoryPages = categoryRepository.findAllByCategoryNameContainingAndStatus(pageable, name, statusValue);
        }

        ids = categoryPages.get().filter(category ->
                        category.getParentCategory() != null && category.getParentCategory().getId() != null)
                .map(Category::getId).distinct().toList();

        if (ids.isEmpty()) {
            categoryNames = null;
        } else {
            categoryNames = categoryRepository.findCategoryNameById(ids).stream()
                    .collect(Collectors.toMap(object -> (Integer) object[0], object -> (String) object[1]));
        }

        if (categoryNames != null) {
            categoryResponses = categoryPages.get().map(category -> {
                CategoryResponse categoryResponse = categoryMapper.toEntityDto(category);
                if (category.getParentCategory() != null) {
                    categoryResponse.setParentCategoryName(categoryNames.get(category.getParentCategory().getId()));
                } else {
                    categoryResponse.setParentCategoryName(null);
                }
                return categoryResponse;
            }).toList();
        } else {
            categoryResponses = categoryPages.get().map(categoryMapper::toEntityDto).toList();
        }

        return PageResponse.<List<CategoryResponse>>builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(categoryPages.getTotalElements())
                .totalPages(categoryPages.getTotalPages())
                .items(categoryResponses)
                .build();
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
        String categoryName = request.getCategoryName().trim();
        request.setCategoryName(categoryName);

        if (categoryRepository.existsByCategoryName(request.getCategoryName()))
            throw new InvalidDataException("Category name already exists");

        if (request.getSlug() != null && request.getSlug().isBlank()) {
            request.setSlug(request.getSlug().trim());
            if (categoryRepository.existsBySlug(request.getSlug()))
                throw new InvalidDataException("Category slug already exists");
        } else {
            String slug = AppUtils.toSlug(categoryName);
            request.setSlug(slug);
        }

        if (request.getParentId() == null) request.setLevel(1);
        else if (request.getLevel() == 1) request.setLevel(2);
        else if (request.getLevel() == 2) request.setLevel(3);
        else throw new InvalidDataException("Invalid level");
    }

    @Override
    protected void beforeUpdate(UpdateCategoryRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateCategoryRequest request, Category entity) {


    }

    @Override
    protected void afterConvertUpdateRequest(UpdateCategoryRequest request, Category entity) {
        Optional<Category> categoryByName = categoryRepository.findByCategoryName(request.getCategoryName());
        if (categoryByName.isPresent() && !categoryByName.get().getId().equals(entity.getId())) {
            throw new InvalidDataException("Category name already exists");
        }

        Optional<Category> categoryBySlug = categoryRepository.findBySlug(request.getSlug());
        if (categoryBySlug.isPresent() && !categoryBySlug.get().getId().equals(entity.getId())) {
            throw new InvalidDataException("Category slug already exists");
        }

        // change parent category
    }

    @Override
    protected String getEntityName() {
        return "Category";
    }

}
