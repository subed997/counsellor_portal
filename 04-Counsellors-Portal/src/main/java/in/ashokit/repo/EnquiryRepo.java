package in.ashokit.repo;

import in.ashokit.entites.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {

//    public List<Enquiry> findByCounsellorId(Integer counsellorId);
//    select * from enq_tbl where counsellor_id=?
    public List<Enquiry> findByCounsellorCounsellorId(Integer counsellorId);
}
