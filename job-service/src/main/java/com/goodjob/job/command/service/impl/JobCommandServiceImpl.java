package com.goodjob.job.command.service.impl;

import com.goodjob.job.command.service.JobCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the JobCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobCommandServiceImpl implements JobCommandService {
} 