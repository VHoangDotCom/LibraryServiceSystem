package com.library.service;

import com.library.entity.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment saveAttachment (MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
}
