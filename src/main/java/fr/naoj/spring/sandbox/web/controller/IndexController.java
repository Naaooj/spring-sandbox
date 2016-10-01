package fr.naoj.spring.sandbox.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof SocialUserDetails) {
			mv.getModel().put("user", ((SocialUserDetails) principal).getUserId());
		} else if (principal instanceof UserDetails) {
			mv.getModel().put("user", ((UserDetails) principal).getUsername());
		}
		return mv;
	}
}
