// ResponseRepository.java
package com.survey.repository;

import com.survey.entity.Response;
import com.survey.entity.Survey;
import com.survey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findBySurvey(Survey survey);
    Optional<Response> findBySurveyAndUser(Survey survey, User user);
    long countBySurvey(Survey survey);
}
