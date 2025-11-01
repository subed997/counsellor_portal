package in.ashokit.service;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entites.Counsellor;

public interface CounsellorService {
    public  Counsellor login(String email, String pwd);

    public boolean register(Counsellor counsellor);

    public DashboardResponseDto getDashboardInfo(Integer counsellorId);
}
