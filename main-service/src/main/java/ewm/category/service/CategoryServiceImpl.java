package ewm.category.service;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.repository.CategoryRepository;
import ewm.event.repository.EventRepository;
import ewm.exception.ConditionNotMetException;
import ewm.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;

    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return categoryMapper.toCategoryDtoList(categories);
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "Категория с ID - " + id + ", не найдена."));
        return categoryMapper.toCategoryDto(category);
    }

    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return categoryMapper.toCategoryDto(
                categoryRepository.save(categoryMapper.toCategoryByNew(newCategoryDto))
        );
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "Категория с id=" + id + " не найдена"));

        boolean hasEvents = eventRepository.existsByCategoryId(id);
        if (hasEvents) {
            throw new ConditionNotMetException("Невозможно удалить категорию: к ней привязаны события");
        }

        categoryRepository.delete(category);
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "Категория с ID - " + id + ", не найдена."));
        category.setName(categoryDto.getName());
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }
}