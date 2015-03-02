When /^I can navigate to the (.*) page$/ do |page|
	wait_for_elements_exist([web_element("#menu-items")], :timeout => Integer(10))
  	page_link = find_list_item("#menu-items", page)
  	touch page_link
	sleep(2)
end
