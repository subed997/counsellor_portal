package in.ashokit.service.impl;
import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entites.Counsellor;
import in.ashokit.entites.Course;
import in.ashokit.entites.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.EnquiryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EnquiryServiceImpl implements EnquiryService {
    @Autowired
    private EnquiryRepo enqRepo;
    @Autowired
    private CounsellorRepo counsellorRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Override
    public boolean insertEnquiry(EnquiryDto enquiryDto, Integer counsellorId)
    {
           Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
           Course course = courseRepo.findById(enquiryDto.getCourseId()).orElseThrow();
              Enquiry enq = new Enquiry();
              // copy dto obj data to entity obj
              BeanUtils.copyProperties(enquiryDto,enq);
              //association
              enq.setCourse(course);
              enq.setCounsellor(counsellor);
              Enquiry savedEnq = enqRepo.save(enq);
              return savedEnq.getEnqId() != null;

    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId) {
        return enqRepo.findByCounsellorCounsellorId(counsellorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterDto, Integer counsellorId) {
        Enquiry enq = new Enquiry();

        // QBE implementation (Query By Example for Dynamic Query Preparation)

        Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
        enq.setCounsellor(counsellor);

        if(filterDto.getClassMode() != null && !"".equals(filterDto.getClassMode())) {
            enq.setClassMode(filterDto.getClassMode());
        }

        if(filterDto.getCourseId() != null && filterDto.getCourseId() > 0) {
            Course course = courseRepo.findById(filterDto.getCourseId()).orElseThrow();
            enq.setCourse(course);
        }

        if(filterDto.getEnqStatus() != null && !"".equals(filterDto.getEnqStatus())) {
            enq.setEnqStatus(filterDto.getEnqStatus());
        }
       return enqRepo.findAll(Example.of(enq));
    }

    @Override
    public Enquiry getEnquiry(Integer enqId) {
        return enqRepo.findById(enqId).orElseThrow();
    }

    @Override
    public boolean updateEnquiry(EnquiryDto enquiryDto) {
        Optional<Enquiry> byId = enqRepo.findById(enquiryDto.getEnqId());

        if(byId.isPresent()) {
            Enquiry enquiry = byId.get();
            enquiry.setEnqStatus(enquiryDto.getEnqStatus());
            enqRepo.save(enquiry);
            return true;
        }
        return false;
    }
    @Override
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}
