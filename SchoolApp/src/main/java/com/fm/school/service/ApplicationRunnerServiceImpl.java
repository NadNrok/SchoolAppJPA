package com.fm.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fm.school.dao.ApplicationRunnerDAO;

@Service
public class ApplicationRunnerServiceImpl implements ApplicationRunnerService{

    private final ApplicationRunnerDAO applicationRunnerDAO;

    @Autowired
    public ApplicationRunnerServiceImpl(ApplicationRunnerDAO applicationRunnerDAO) {
        this.applicationRunnerDAO = applicationRunnerDAO;
    }

    public boolean databaseHasData() {
        return applicationRunnerDAO.databaseHasData();
    }
}