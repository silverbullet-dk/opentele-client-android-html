And /^change my password to (.*)$/ do |new_password|
	wait_for_elements_exist([web_element("#new-password")], :timeout => Integer(10))
	enter_text web_element("#new-password"), new_password
	enter_text web_element("#new-password-repeat"), new_password
end

And /^submit my new password$/ do
	hide_keyboard()
	scroll_to_element("input[type=submit]")
	touch web_element("input[type=submit]")
end
