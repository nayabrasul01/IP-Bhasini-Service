package com.esic.checklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import com.esic.checklist.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
	List<Response> findByUserIdAndQuestionnaire(String userId, Questionnaire questionnaire);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO checklist_responses (questionnaire_id, question_id, user_id, response_value, created_by) "
			+ "VALUES (:questionnaireId, :questionId, :userId, :responseValue, :created_by) "
			+ "ON DUPLICATE KEY UPDATE response_value = VALUES(response_value)", nativeQuery = true)
	void upsertResponse(@Param("questionnaireId") Long questionnaireId, @Param("questionId") Long questionId,
			@Param("userId") String userId, @Param("responseValue") String responseValue, @Param("created_by") String created_by);

	List<Response> findByUserIdAndQuestionnaireAndQuestion(Long userId, Questionnaire questionnaire, Question question);
}
