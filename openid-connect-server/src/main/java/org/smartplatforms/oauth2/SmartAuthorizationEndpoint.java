package org.smartplatforms.oauth2;

import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@SessionAttributes("authorizationRequest")
@RequestMapping(value = "/authorize")
public class SmartAuthorizationEndpoint extends AuthorizationEndpoint implements
		InitializingBean {

	@RequestMapping
	@Override
	public ModelAndView authorize(Map<String, Object> model,
			@RequestParam Map<String, String> parameters,
			SessionStatus sessionStatus, Principal principal) {
		
		 ModelAndView mv  =  super.authorize(model, parameters, sessionStatus, principal);

   		 AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
  
   		
   		 //TODO if launch context is needed, redirect to an external service to go and get it
		if (authorizationRequest!= null && authorizationRequest.getExtensions().containsKey("external_launch_required")) {
			return new ModelAndView(new RedirectView(
					"https://fhir.me?" + authorizationRequest.getExtensions().get("external_launch_request").toString()));
		}
		
		 
		 // Plan: for SMART requests that need patient-level context, redirect to a patient-picker, passing CSRF token
		 // For SMART request that don't need patient-level context, redirect to a simplified approval screen
		 // be sure to have automatic approval of the trusted context-picking app, so it always knows the current user.

		 return mv;
	}
	
}
