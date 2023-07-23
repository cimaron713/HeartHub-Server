package com.umc_spring.Heart_Hub.Report.repository;

import com.umc_spring.Heart_Hub.Report.model.UserReport;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    UserReport findByReporterAndReported(User reporter, User reported);
    List<UserReport> findByReported(User reported);
}
