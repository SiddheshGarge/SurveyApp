// OptionRepository.java
package com.survey.repository;

import com.survey.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
