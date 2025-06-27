package com.lutu.page_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CampController {
	
	@GetMapping("/searchresults")
	public String login() {
		return "back-end/camp/search-results.html"; // view
	}

}
