package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.userChatDAO;
import com.example.wearVillage.DTO.USER_INFO_DTO;
import com.example.wearVillage.Entity.USER_INFO;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class KYHController {
    private final Repository_USER_INFO rep_user_info;

    private final userChatDAO userDAO;

    KYHController(userChatDAO userDAO, Repository_USER_INFO rep_user_info) {
        this.userDAO = userDAO;
        this.rep_user_info = rep_user_info;
    }


    @GetMapping(value ="/logout")
    public String logout(HttpSession session){
        log.info((String) session.getAttribute("nickname")+"님이 로그아웃하셨습니다.");
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping(value = "/chat")
    public String chat(Model model,
                        HttpSession session,
                       @RequestParam String postSubtitle,
                       @RequestParam String postWriterId,
                       @RequestParam String postPrice,
                       @RequestParam String postRentDefaultPrice,
                       @RequestParam String postRentDayPrice,
                       @RequestParam String postThumbnailUrl,
                       @RequestParam String postMapInfo,
                       @RequestParam String postId) {
        if(session.getAttribute("email")!=null) {
            model.addAttribute("postSubtitle", postSubtitle);
            model.addAttribute("postWriterId", postWriterId);
            model.addAttribute("postPrice", postPrice);
            model.addAttribute("postRentDefaultPrice", postRentDefaultPrice);
            model.addAttribute("postRentDayPrice", postRentDayPrice);
            model.addAttribute("postThumbnailUrl", postThumbnailUrl);
            model.addAttribute("postMapInfo", postMapInfo);
            model.addAttribute("postId", postId);
            model.addAttribute("myId", session.getAttribute("nickname"));
            model.addAttribute("theme", session.getAttribute("theme"));
//        List<Map<String, Object>> chatHistory = userDAO.oracle_to_userChat(id, post_id);
//        model.addAttribute("chat_history", chatHistory);
            return "chat.html";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/chatroom")
    public String chatroom(HttpSession session){
        session.getAttribute("id");

        return "chatroom.html";
    }
    @GetMapping(value = "/chatroom")
    public String getchatroom(HttpServletRequest request){
        return "chatroom.html";
    }

    @ResponseBody
    @PostMapping(value = "/userInfo")
    public List<USER_INFO> userId(HttpSession session) throws IndexOutOfBoundsException{
        try {
            String email = (String) session.getAttribute("email");
            return rep_user_info.findByEMAIL(email);

        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/finished_signUp")
    @ResponseBody
    public String signup(@RequestBody USER_INFO user_info) throws Exception {
        try {
            rep_user_info.save(user_info);
            System.out.println("사용자 회원가입 : "+user_info.toString());
            return "ok";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "no";
        }
    }

    @GetMapping(value ="/testPlace")
    public String testPlace(){
        return "datatest.html";
    }


    @GetMapping(value="/kyhTest")
    public String kyhTest(){
        return "";
    }

}

