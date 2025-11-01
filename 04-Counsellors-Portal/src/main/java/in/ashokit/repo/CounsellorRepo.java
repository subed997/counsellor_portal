package in.ashokit.repo;

import in.ashokit.entites.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounsellorRepo extends JpaRepository <Counsellor, Integer> {
//    select * from counsellor where email=? and pwd=?
    public Counsellor findByEmailAndPwd(String email, String pwd);
}
