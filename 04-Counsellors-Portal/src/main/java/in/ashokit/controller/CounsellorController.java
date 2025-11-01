package in.ashokit.controller;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entites.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CounsellorController {
    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
    public String index(Model model) {
        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);
        return "index";
    }


    @PostMapping("/login")
    public String handleLogin(Counsellor counsellor, Model model, HttpServletRequest req) {
        Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

        if (c == null) {
            model.addAttribute("emsg", "Invalid Credentials");
            return "index";

        } else {
            HttpSession session = req.getSession(true);
            session.setAttribute("CID", c.getCounsellorId());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(Counsellor counsellor, Model model) {
        boolean register = counsellorService.register(counsellor);
        if (register) {
            model.addAttribute("smsg", "Registration Success");
        } else {
            model.addAttribute("emsg", "Registration Failed");
        }
        return "register";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model,HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");
        DashboardResponseDto dashboardInfoObj = counsellorService.getDashboardInfo(counsellorId);
        model.addAttribute("dashboardInfo", dashboardInfoObj);
        return "dashboard";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
