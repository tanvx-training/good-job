package com.goodjob.job.domain.posting.command;

import com.goodjob.job.domain.posting.dto.CreatePostingCommand;

public interface PostingCommandService {

    Long createPostingJob(CreatePostingCommand command);
}
