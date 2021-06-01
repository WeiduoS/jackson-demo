package domain;

import java.util.List;
import java.util.Objects;

/**
 * @author Weiduo
 * @date 2021/5/31 - 9:28 下午
 */
public class Question {
    private String question;
    private List<String> options;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question.equals(question1.question) &&
                options.equals(question1.options) &&
                answer.equals(question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, options, answer);
    }
}
