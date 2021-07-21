package com.joshuagossett.shiftmaster

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.Version

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.time.LocalDateTime

@Entity
class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    LocalDateTime startTime
    LocalDateTime endTime

    @JsonIgnore
    boolean isSoftDeleted

    @CreationTimestamp
    @JsonIgnore
    LocalDateTime createdOn

    @UpdateTimestamp
    @JsonIgnore
    LocalDateTime updatedOn

    @Version
    @JsonIgnore
    Long version = 1L
}
