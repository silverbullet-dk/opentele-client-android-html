And /^I should see a list of questionnaires$/ do
  wait_for_elements_exist([web_element("#questionnaire-items")],
                          :timeout => Integer(10))
  first_questionnaire_title = query(web_element("#questionnaire-items"),
                                    "textContent")[0]
  matchThread = (/Blodsukker/i =~ first_questionnaire_title)
  assert(matchThread, "Couldn't find expected questionnaire.")
end

Then /^I can navigate to the Blodsukker questionnaire$/ do
  wait_for_elements_exist([web_element("#questionnaire-items")],
                          :timeout => Integer(10))
  questionnaire_link = find_list_item("#questionnaire-items", "Blodsukker")
  touch questionnaire_link

  wait_for_elements_exist([web_element("#back-button")], :timeout => Integer(10))
end

Then /^I can fill out the Blodsukker questionnaire$/ do
  # Page 1
  wait_for_elements_exist([web_element("#question-center-button")],
                          :timeout => Integer(10))
  touch web_element("#question-center-button")

  # Page 2
  wait_for_elements_exist([web_element("#count")],
                          :timeout => Integer(10))
  enter_text web_element("#count"), 2
  hide_keyboard()
  touch web_element("#afterMeal")
end

And /^send reply$/ do
    scroll_down()
    touch web_element("#question-right-button")

    # Page 3
    wait_for_elements_exist([web_element("#send-reply-yes-button")],
                            :timeout => Integer(10))
    touch web_element("#send-reply-yes-button")

    # Page 4
    wait_for_elements_exist([web_element("#ack-button")],
                            :timeout => Integer(10))
end

And /^go back to menu$/ do
    touch web_element("#ack-button")
end
