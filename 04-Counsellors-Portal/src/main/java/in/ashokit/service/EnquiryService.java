package in.ashokit.service;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entites.Course;
import in.ashokit.entites.Enquiry;

import java.util.List;

public interface EnquiryService {

    public List<Course> getCourses();
    public boolean insertEnquiry(EnquiryDto enquiryDto, Integer counsellorId);

    public List<Enquiry> getAllEnquiries(Integer counsellorId);

    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterDto, Integer counsellorId);

    public Enquiry getEnquiry(Integer enqId);
//    Update +Insert  = Upsert
    public boolean updateEnquiry(EnquiryDto enquiry);

}
