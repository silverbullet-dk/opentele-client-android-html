package dk.silverbullet.telemed.reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Encapsulates a collection of upcoming reminders.
 */
public class UpcomingReminders {

    // --*-- Fields --*--

    private Date baselineDate;
    private List<ReminderBean> reminders;

    // --*-- Constructors --*--

    public UpcomingReminders(Date baselineDate, List<ReminderBean> reminders) {
        this.baselineDate = baselineDate;
        this.reminders = reminders;
    }

    public UpcomingReminders(long baselineDateAsLong, List<ReminderBean> reminders) {
        this.baselineDate = new Date(baselineDateAsLong);
        this.reminders = reminders;
    }

    // --*-- Methods --*--

    /**
     * Returns the next reminder.
     *
     * @return -
     */
    public Date nextReminder() {
        // We know that we have at least one upcoming reminder, so we'll never actually return Long.MAX_VALUE
        long offsetToNextReminder = Long.MAX_VALUE;

        for (ReminderBean reminderBean : getReminderBeans()) {
            for (Long alarm : reminderBean.getAlarms()) {
                offsetToNextReminder = Math.min(offsetToNextReminder, alarm);
            }
        }

        return dateFromOffset(offsetToNextReminder);
    }

    /**
     * Removes all reminder beans that would be "fired" at a given time.
     *
     * @param time -
     */
    public void removeRemindersBeforeOrAt(Date time) {
        long offset = offsetFromDate(time);
        List<ReminderBean> newReminderBeans = new ArrayList<ReminderBean>();

        for (ReminderBean reminderBean : getReminderBeans()) {
            List<Long> alarms = new ArrayList<Long>();
            for (Long alarm : reminderBean.getAlarms()) {
                if (alarm > offset) {
                    alarms.add(alarm);
                }
            }

            if (!alarms.isEmpty()) {
                ReminderBean newReminderBean = new ReminderBean();
                newReminderBean.setQuestionnaireId(reminderBean.getQuestionnaireId());
                newReminderBean.setQuestionnaireName(reminderBean.getQuestionnaireName());
                newReminderBean.setAlarms(alarms);
                newReminderBeans.add(newReminderBean);
            }
        }

        setReminderBeans(newReminderBeans);
    }

    /**
     * Returns all questionnaires with alarms triggered before the specified
     * time.
     *
     * @param time -
     * @return -
     */
    public List<String> remindedQuestionnairesAt(Date time) {
        long offset = offsetFromDate(time);
        List<String> result = new ArrayList<String>();

        for (ReminderBean reminderBean : reminders) {
            for (Long alarm : reminderBean.getAlarms()) {
                if (alarm <= offset) {
                    result.add(reminderBean.getQuestionnaireName());
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Removes the questionnaire with the specified name.
     *
     * @param questionnaireName -
     */
    public void removeQuestionnaire(String questionnaireName) {
        List<ReminderBean> newReminderBeans = new ArrayList<ReminderBean>();

        for (ReminderBean reminderBean : getReminderBeans()) {
            if (!questionnaireName.equals(reminderBean.getQuestionnaireName())) {
                newReminderBeans.add(reminderBean);
            }
        }

        setReminderBeans(newReminderBeans);
    }

    private Date dateFromOffset(long offset) {
        return new Date(baselineDate.getTime() + offset * 1000);
    }

    private long offsetFromDate(Date date) {
        return (date.getTime() - baselineDate.getTime()) / 1000;
    }

    public long getBaselineDateAsLong() {
        return baselineDate.getTime();
    }

    public List<ReminderBean> getReminderBeans() {
        return reminders;
    }

    public void setReminderBeans(List<ReminderBean> reminderBeans) {
        this.reminders = reminderBeans;
    }

    public boolean hasMoreReminders() {
        return !reminders.isEmpty();
    }
}
