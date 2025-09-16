package com.esic.checklist.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;

public class QuestionStub {

    public static List<Question> getDummyQuestions() {
        List<Question> questions = new ArrayList<>();

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId((long) 1);
        questionnaire.setName("Swasth Nari, Sashakt Parivar Abhiyaan");
        questionnaire.setCreatedBy(null);
        questionnaire.setCreatedAt(LocalDateTime.parse("2025-09-13T13:34:23"));

        String[] texts = {
                "Have you been coughing for more than 2 weeks?",
                "Have you noticed blood in your sputum?",
                "Have you had a fever lasting more than 2 weeks?",
                "Do you have white/red patches, ulcers, or growths in your mouth that have not healed for more than 2 weeks?",
                "Do you face difficulty in opening mouth?",
                "Do you have pale skin, brittle nails, or frequent mouth ulcers?",
                "Have you noticed a lump in your breast?",
                "Have you seen blood-stained discharge from the nipple?",
                "Have you observed any change in the shape or size of your breast?",
                "Have you had bleeding between periods?",
                "Do you experience an irregular menstrual cycle?",
                "Do you have heavy menstrual bleeding?",
                "Have you experienced bleeding after menopause?",
                "Have you experienced bleeding after intercourse?",
                "Have you had foul-smelling vaginal discharge?",
                "Do you have a history of repeated episodes of severe pain in bones/joints?",
                "Has any family member been diagnosed with sickle cell disease/trait?",
                "Do you often suffer from jaundice (yellowing of eyes/skin) or anemia since childhood?"
        };

        for (int i = 0; i < texts.length; i++) {
            Question q = new Question();
            q.setQuestionId((long) (i + 1));
            q.setQuestionnaire(questionnaire);
            q.setQuestionText(texts[i]);
            q.setIsRequired(false);
            questions.add(q);
        }

        return questions;
    }
}

