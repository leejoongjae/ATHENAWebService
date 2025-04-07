package kr.co.athena.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.athena.oauth.model.Oauth;


@Controller
public class MainController {
	
	@RequestMapping(value="/")
	public String mainHome() {
	    return "index";
	}
	
	@RequestMapping(value="/login")
	public String loginHome(Oauth vo, ModelMap model, HttpServletRequest request, HttpSession session) {
		String rtnUrl = "main/login";
		
		/*User user = userService.getEngUserInfo(vo);
		if(user != null) {
			rtnUrl =  "redirect:"+"/quizeMain?androidId="+vo.getAndroidId();
		}*/
		
		request.setAttribute("vo", vo);
	    return rtnUrl;
	}
	
}
