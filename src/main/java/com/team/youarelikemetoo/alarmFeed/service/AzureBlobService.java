package com.team.youarelikemetoo.alarmFeed.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class AzureBlobService {

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Autowired
    private BlobServiceClient blobServiceClient;

    public String uploadAlarmFeedImage(MultipartFile file, String blobPath){
        try{
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobPath);

            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();

        } catch (IOException e) {
            throw new RuntimeException("Azure Blob 업로드 실패", e);
        }
    }

    public void deleteImage(String filePath){
        try{
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            BlobClient blobClient = containerClient.getBlobClient(filePath);
            if(blobClient.exists()){
                blobClient.delete();
            } else{
                log.info(filePath + "Img not found");
            }


        } catch (Exception e) {
            throw new RuntimeException("Azure Blob 업로드 실패", e);
        }
    }

    public void deleteFolder(String folderPath) {
        try{
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            for(BlobItem blobItem : containerClient.listBlobsByHierarchy(folderPath + "/")){
                containerClient.getBlobClient(blobItem.getName()).delete();
            }

        } catch (Exception e) {
            throw new RuntimeException("Azure Blob 업로드 실패", e);
        }


    }
}
