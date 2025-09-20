package com.esic.checklist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esic.checklist.model.IpVitals;
import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import com.esic.checklist.repository.IpVitalsRepository;
import com.esic.checklist.repository.QuestionRepository;
import com.esic.checklist.repository.QuestionnaireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;
    private final IpVitalsRepository vitalsRepository;

    public List<Question> getQuestions(Long questionnaireId) {
        Questionnaire q = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found"));
        return questionRepository.findByQuestionnaire(q);
    }

    public IpVitals saveVitals(IpVitals vitals) {
        return vitalsRepository.save(vitals);
    }

	public List<IpVitals> getVitals(String ipNumber) {
		return vitalsRepository.findByIpNumber(ipNumber);
	}
}
