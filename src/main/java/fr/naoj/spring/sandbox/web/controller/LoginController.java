package fr.naoj.spring.sandbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class LoginController {

    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Autowired
    private MessageSource messageSource;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		Throwable throwable = (Throwable) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (throwable instanceof BadCredentialsException) {
			model.addAttribute(ERROR, Boolean.TRUE);
            model.addAttribute(ERROR_MESSAGE, messageSource.getMessage("account.badCredential", null, request.getLocale()));
		} else if (throwable instanceof DisabledException) {
            model.addAttribute(ERROR, Boolean.TRUE);
            model.addAttribute(ERROR_MESSAGE, messageSource.getMessage("account.disabled", null, request.getLocale()));
		} else {
			model.addAttribute("exception", throwable == null ? null : throwable.getMessage());
		}

        // Avoid to redisplay the error after a refresh
        request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		return "login";
	}
}
