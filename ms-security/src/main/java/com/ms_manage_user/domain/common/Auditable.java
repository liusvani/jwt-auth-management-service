package com.ms_manage_user.domain.common;



import java.time.LocalDateTime;

public interface Auditable {
    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updatedAt);
}
