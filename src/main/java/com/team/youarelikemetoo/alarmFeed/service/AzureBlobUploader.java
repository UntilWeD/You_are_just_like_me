package com.team.youarelikemetoo.alarmFeed.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class AzureBlobUploader {

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Autowired
    private BlobServiceClient blobServiceClient;

    public String upload(MultipartFile file){
        try{
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();

        } catch (IOException e) {
            throw new RuntimeException("Azure Blob 업로드 실패", e);
        }
    }

}
