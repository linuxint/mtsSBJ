package com.devkbil.mtssbj.member;

import com.devkbil.mtssbj.common.LocaleMessage;
import com.devkbil.mtssbj.member.exception.MemberNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class LoginController {
    private static final Integer cookieExpire = 60 * 60 * 24 * 30; // 1 month

    @Autowired
    LocaleMessage localeMessage;

    @Autowired
    private MemberService memberService;

    /**
     * 쿠키 저장.
     */
    public static void set_cookie(String cid, String value, HttpServletResponse res) {

        Cookie ck = new Cookie(cid, value);
        ck.setPath("/");
        ck.setMaxAge(cookieExpire);
        res.addCookie(ck);
    }

    /**
     * 쿠키 가져오기.
     */
    public static String get_cookie(String cid, HttpServletRequest request) {
        String ret = "";

        if (request == null) {
            return ret;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ret;
        }

        for (Cookie ck : cookies) {
            if (ck.getName().equals(cid)) {
                ret = ck.getValue();

                ck.setMaxAge(cookieExpire);
                break;
            }
        }
        return ret;
    }

    @RequestMapping(value = "memberLoginError")
    public String memberLoginError(HttpServletRequest request, ModelMap modelMap) {
        //String userid = get_cookie("sid", request);

        //modelMap.addAttribute("userid", userid);
        modelMap.addAttribute("msg", "로그인 할 수 없습니다.");
        return "common/message";
    }

    /**
     * 로그인화면.
     */
    @RequestMapping(value = "memberLogin")
    public String memberLogin(HttpServletRequest request, ModelMap modelMap) {
        String userid = get_cookie("sid", request);

        modelMap.addAttribute("userid", userid);

        return "member/memberLogin";
    }

    /**
     * 로그인 처리.
     */
    @RequestMapping(value = "memberLoginChk")
    public String memberLoginChk(HttpServletRequest request, HttpServletResponse response, UserVO loginInfo,
            ModelMap modelMap, @AuthenticationPrincipal User user) {

        Optional<UserVO> findOne = memberService.findOne(user.getUsername());
        UserVO userVO = findOne.orElseThrow(() -> new MemberNotFoundException(user.getUsername()));

        if (userVO == null) {
            modelMap.addAttribute("msg", "로그인 할 수 없습니다.");
            return "common/message";
        }

        memberService.insertLogIn(userVO.getUserno());

        HttpSession session = request.getSession();

        session.setAttribute("userid", userVO.getUserid());
        session.setAttribute("userrole", userVO.getUserrole());
        session.setAttribute("userno", userVO.getUserno());
        session.setAttribute("usernm", userVO.getUsernm());

        if ("Y".equals(loginInfo.getRemember())) {
            set_cookie("sid", loginInfo.getUserid(), response);
        } else {
            set_cookie("sid", "", response);
        }

        return "redirect:/index";
    }

    /*
     * -------------------------------------------------------------------------
     */

    /**
     * 로그아웃.
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "memberLogout")
    public String memberLogout(HttpServletRequest request, ModelMap modelMap) {
        HttpSession session = request.getSession();

        String userno = session.getAttribute("userno").toString();

        memberService.insertLogOut(userno);

        session.removeAttribute("userid");
        session.removeAttribute("userrole");
        session.removeAttribute("userno");
        session.removeAttribute("usernm");
        session.removeAttribute("mail");

        return "redirect:/memberLogin";
    }

    /**
     * 사용자가 관리자페이지에 접근하면 오류 출력.
     * @param request
     * @return
     */
    @RequestMapping(value = "noAuthMessage")
    public String noAuthMessage(HttpServletRequest request, ModelMap modelMap) {

        modelMap.put("msg", localeMessage.getMessage("msg.err.noAuth"));

        return "common/noAuth";
    }

}
