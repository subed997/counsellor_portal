package in.ashokit.service.impl;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entites.Counsellor;
import in.ashokit.entites.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CounsellorServiceImpl implements CounsellorService {
    @Autowired
    private CounsellorRepo counsellorRepo;
    @Autowired
    private EnquiryRepo enqRepo;

    @Override
    public Counsellor login(String email, String pwd) {
        return counsellorRepo.findByEmailAndPwd(email, pwd);

    }

    @Override
    public boolean register(Counsellor counsellor) {
        Counsellor savedCounsellor =  counsellorRepo.save(counsellor);
        return savedCounsellor.getCounsellorId() != null;
    }

    @Override
    public DashboardResponseDto getDashboardInfo(Integer counsellorId) {


        List<Enquiry> enqsList = enqRepo.findByCounsellorCounsellorId(counsellorId);
        int totalCnt = enqsList.size();

        Map<String, Long> statusWiseCnt = enqsList.stream()
                                                    .collect(Collectors.groupingBy(Enquiry::getEnqStatus, Collectors.counting()));
        int openCnt = statusWiseCnt.getOrDefault("OPEN",0l).intValue();
        int enrolledCnt = statusWiseCnt.getOrDefault("ENROLLED",0l).intValue();
        int lostCnt = statusWiseCnt.getOrDefault("LOST",0l).intValue();

        DashboardResponseDto responseDto = DashboardResponseDto.builder()
                                                                .totalEnqs(totalCnt)
                                                                .openEnqs(openCnt)
                                                                .enrolledEnqs(enrolledCnt)
                                                                .lostEnqs(lostCnt)
                                                                .build();
        return responseDto;
    }
}
