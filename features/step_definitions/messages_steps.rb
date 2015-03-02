And /^I should see a list of threads$/ do
  wait_for_elements_exist([web_element("#thread-items")],
                          :timeout => Integer(10))
  second_thread_title = query(web_element("#thread-items"),
                                     "textContent")[1]
  matchThread = (/TCN/i =~ second_thread_title)
  assert(matchThread, "Couldn't find expected thread.")
end

Then /^I can navigate to the TCN thread$/ do
	wait_for_elements_exist([web_element("#thread-items")], :timeout => Integer(10))
  thread_link = find_list_item("#thread-items", "TCN")
  touch thread_link
end

And /^I should see a list of messages$/ do
  wait_for_elements_exist([web_element("#message-items")],
                          :timeout => Integer(10))
  first_message_text = query(web_element("#message-items"), "textContent")[0]
  matchMessage = (/Test/i =~ first_message_text)
  assert(matchMessage, "Couldn't find expected thread message.")
end

Then /^I can navigate to new message$/ do
  scroll_to_element("#new-message-button")
  wait_for_elements_exist([web_element("#new-message-button")], :timeout => Integer(10))
  touch web_element("#new-message-button")
end

And /^I can write a new message with topic (.*) and message (.*)$/ do |topic, message|
    wait_for_elements_exist([web_element("#topic")], :timeout => Integer(10))
	enter_text web_element("#topic"), topic
    hide_keyboard()
	enter_text web_element("textarea"), message
    hide_keyboard()
    sleep(1)
	scroll_to_element("#submit")
    wait_for_elements_exist([web_element("input[type=submit]")], :timeout => Integer(10))
    touch web_element("input[type=submit]")
end
