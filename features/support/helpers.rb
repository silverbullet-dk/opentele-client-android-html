def web_element(selector)
	return "ErrorHandlingWebView css:'#{selector}'"
end

def find_list_item(selector, match)
  list_items = query(web_element(selector))
  for i in 0...list_items.length
    if list_items[i]["textContent"].downcase.include? match.downcase then
      return list_items[i]
    end
  end
end

def scroll_to_element(selector)
	while query(web_element(selector)).length == 0 do
		scroll_down()
	end
end

def scroll_up_to_element(selector)
	while query(web_element(selector)).length == 0 do
		scroll_up()
	end
end

def hide_keyboard()
  press_back_button()
end
