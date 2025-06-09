package com.team.youarelikemetoo.alarm.repository;

import com.team.youarelikemetoo.alarm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryJPARepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByCategoryName(String categoryName);
    public Optional<Category> findById(Long id);
}
