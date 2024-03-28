package com.expense.tracker.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    
	List<Category> findAll(Specification<Category> specification);

    default List<Category> findByCatagoryName (SearchDto serachDto){
    	
    	Specification<Category> specification = (root, query, criteriaBuilder) -> {
    		
			List<Predicate> predicates = new ArrayList<>();
		
			if (serachDto.getSerchKeys() != null) {
				predicates.add(criteriaBuilder.or(
						criteriaBuilder.like(root.get("name"), "%" + serachDto.getSerchKeys() + "%")));			
			}
			query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
        return findAll(specification);    	

}

}