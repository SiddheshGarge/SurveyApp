// SurveyRepository.java
package com.survey.repository;

import com.survey.entity.Survey;
import com.survey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByCreator(User creator);
    List<Survey> findByActiveTrue();
}
