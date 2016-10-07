package fr.naoj.spring.sandbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		
		Throwable throwable = (Throwable) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (throwable instanceof BadCredentialsException) {
			model.addAttribute("error", Boolean.TRUE);
		} else {
			model.addAttribute("exception", throwable == null ? null : throwable.getMessage());
		}
		
		return "login";
	}
}
