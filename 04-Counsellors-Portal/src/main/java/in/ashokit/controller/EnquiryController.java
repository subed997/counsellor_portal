package in.ashokit.controller;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;

import in.ashokit.entites.Enquiry;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnquiryController {
    @Autowired
    private EnquiryService enqService;

    @GetMapping("/enquiry")
    public String loadAddEnqForm(Model model){
        model.addAttribute("enqDto",new EnquiryDto());
        model.addAttribute("courses",enqService.getCourses());
        return "add-enq";
    }
    @GetMapping("/editEnq")
    public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model){
        Enquiry enquiry = enqService.getEnquiry(enqId);
        EnquiryDto enqDtoObj = new EnquiryDto();
        BeanUtils.copyProperties(enquiry,enqDtoObj);
        enqDtoObj.setCourseId(enquiry.getCourse().getCourseId());
        model.addAttribute("enqDto",enqDtoObj);
        model.addAttribute("courses",enqService.getCourses());
        return "add-enq";
    }
    @PostMapping("/enquiry")
    public String addEnquiry(@ModelAttribute("enqDto") EnquiryDto enqDto, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");

        boolean isSaved = enqService.insertEnquiry(enqDto, counsellorId);

        if (isSaved) {
            model.addAttribute("smsg","Enquiry Added");
        } else {
            model.addAttribute("emsg", "Failed to Add Enquiry");
        }
        model.addAttribute("courses",enqService.getCourses());
        return "add-enq";
    }
    @GetMapping("/view-enquiries")
    public String viewEnquiries(Model model, HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");

        List<Enquiry> enqsList= enqService.getAllEnquiries(counsellorId);

        model.addAttribute("filter", new EnqFilterRequestDto());
        model.addAttribute("courses",enqService.getCourses());
        model.addAttribute("enqs",enqsList);
        return "view-enqs";
    }
    @PostMapping("/filter-enquiries")
    public String filterEnqs(@ModelAttribute("filter") EnqFilterRequestDto filter, Model model, HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");

        List<Enquiry> enqsList= enqService.getEnquiriesWithFilter(filter, counsellorId);
        model.addAttribute("courses",enqService.getCourses());
        model.addAttribute("enqs",enqsList);
        return "view-enqs";
    }

}
