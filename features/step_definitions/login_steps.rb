When /^I am logged in as (.*) with password (.*)$/ do |user_name, password|
	wait_for_elements_exist([web_element("#username")], :timeout => Integer(10))
	enter_text web_element("#username"), user_name
	enter_text web_element("#password"), password
	touch web_element("input[type=submit]")
end

Then /^I should see the menu$/ do
	wait_for_elements_exist([web_element("#menu-list")], :timeout => Integer(10))
	text = query(web_element(".title"), "textContent")[0]
	assert_equal('Menu', text, "Login failed. Should see menu page")
end
