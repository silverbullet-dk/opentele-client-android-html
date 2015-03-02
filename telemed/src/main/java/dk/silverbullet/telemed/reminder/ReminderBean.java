package dk.silverbullet.telemed.reminder;

import java.io.Serializable;
import java.util.List;

/**
 * Encapsulates a reminder.
 */
public class ReminderBean implements Serializable {

    // --*-- Fields --*--

    private long questionnaireId;
    private String questionnaireName;
    private List<Long> alarms;

    // --*-- Constructors --*--

    public ReminderBean() { }

    // --*-- Methods --*--

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ReminderBean: " +
                "{ questionnaireId: " + questionnaireId +
                ", questionnaireName: " + questionnaireName +
                ", alarms: " + alarms +
                " }";
    }

    // -*- Getters/Setters -*-

    public long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public List<Long> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Long> alarms) {
        this.alarms = alarms;
    }

}
