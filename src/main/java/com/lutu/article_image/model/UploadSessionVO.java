package com.lutu.article_image.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Entity
@Table(name = "article_upload_session")
public class UploadSessionVO {

    @Id
    @Column(name = "upload_id", length = 64)
    private String uploadId; // 上傳會話ID

    @Column(name = "total_chunks")
    private Integer totalChunks; // 總分塊數

    @Column(name = "chunk_size")
    private Long chunkSize; // 分塊大小

    @Column(name = "total_size")
    private Long totalSize; // 總檔案大小

    @Column(name = "file_name", length = 255)
    private String fileName; // 原始檔案名

    @Column(name = "content_type", length = 100)
    private String contentType; // 檔案類型

    @Column(name = "uploaded_chunks", columnDefinition = "TEXT")
    private String uploadedChunks; // 已上傳的分塊列表（JSON格式）

    @Column(name = "created_time")
    private LocalDateTime createdTime; // 創建時間

    @Column(name = "status")
    private String status; // 狀態：UPLOADING, COMPLETED, FAILED, EXPIRED

    @Column(name = "article_id")
    private Integer articleId; // 關聯的文章ID（可選）

    public UploadSessionVO() {
        this.createdTime = LocalDateTime.now();
        this.status = "UPLOADING";
        this.uploadedChunks = "[]";
    }

    public UploadSessionVO(String uploadId, Integer totalChunks, Long chunkSize,
            Long totalSize, String fileName, String contentType) {
        this();
        this.uploadId = uploadId;
        this.totalChunks = totalChunks;
        this.chunkSize = chunkSize;
        this.totalSize = totalSize;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    // Getters and Setters
    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUploadedChunks() {
        return uploadedChunks;
    }

    public void setUploadedChunks(String uploadedChunks) {
        this.uploadedChunks = uploadedChunks;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "UploadSessionVO{" +
                "uploadId='" + uploadId + '\'' +
                ", totalChunks=" + totalChunks +
                ", chunkSize=" + chunkSize +
                ", totalSize=" + totalSize +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", status='" + status + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}