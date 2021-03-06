package demo.zcy.carforum.controller;

//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.zcy.carforum.dto.AccessTokenDTO;
import demo.zcy.carforum.dto.GithubUser;
import demo.zcy.carforum.provider.GithubProvider;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;
    
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();        
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);        
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }

    // @GetMapping("/logout")
    // public String logout(HttpServletRequest request,
    //                      HttpServletResponse response) {
    //     request.getSession().removeAttribute("user");
    //     Cookie cookie = new Cookie("token", null);
    //     cookie.setMaxAge(0);
    //     response.addCookie(cookie);
    //     return "redirect:/";
    // }
}