package org.practiceprojects.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "monolith_questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String difficultylevel;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctanswer;
}
