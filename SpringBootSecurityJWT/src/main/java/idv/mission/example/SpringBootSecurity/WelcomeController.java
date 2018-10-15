package idv.mission.example.SpringBootSecurity;

import static idv.mission.example.SpringBootSecurity.jwt.JWT_Constants.HEADER_STRING;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class WelcomeController {
    @RequestMapping("/login")
    public @ResponseBody String login(HttpServletRequest request, HttpServletResponse response) {
        String token = response.getHeader(HEADER_STRING);
        Map<String, String> result = new HashMap<String, String>();
        result.put("username", request.getParameter("username"));
        result.put("password", request.getParameter("password"));
        result.put("token", token);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    @RequestMapping("/hello")
    public @ResponseBody String hello() {
        return "Hello";
    }

    @RequestMapping("/hello/{name}")
    public @ResponseBody String hello(@PathVariable String name) {
        return "Hello, " + name;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth != null ) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}