# coding: utf-8
Then /^I should see a list of acknowledgements$/ do
  wait_for_elements_exist([web_element("#acknowledgement-items")],
                          :timeout => Integer(10))
  first_acknowledgement_text = query(web_element("#acknowledgement-items"),
                                     "textContent")[0]
  matchAcknowledgement = (/spørgeskema/i =~ first_acknowledgement_text)
  assert(matchAcknowledgement, "Couldn't find expected acknowledgement receipt.")
end
