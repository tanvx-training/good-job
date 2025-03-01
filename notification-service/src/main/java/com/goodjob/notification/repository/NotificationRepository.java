package com.goodjob.notification.repository;

import com.goodjob.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    /**
     * Finds all notifications for a user.
     *
     * @param userId the user ID
     * @param pageable the pagination information
     * @return the page of notifications
     */
    Page<Notification> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    /**
     * Finds all unread notifications for a user.
     *
     * @param userId the user ID
     * @return the list of unread notifications
     */
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(String userId);

    /**
     * Counts unread notifications for a user.
     *
     * @param userId the user ID
     * @return the count of unread notifications
     */
    long countByUserIdAndReadFalse(String userId);

    /**
     * Finds a notification by ID and user ID.
     *
     * @param id the notification ID
     * @param userId the user ID
     * @return the optional notification
     */
    Optional<Notification> findByIdAndUserId(UUID id, String userId);

    /**
     * Marks all notifications as read for a user.
     *
     * @param userId the user ID
     * @return the number of updated notifications
     */
    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.userId = :userId AND n.read = false")
    int markAllAsRead(@Param("userId") String userId);

    /**
     * Deletes all notifications for a user.
     *
     * @param userId the user ID
     */
    void deleteByUserId(String userId);

    /**
     * Finds notifications by source ID and source type.
     *
     * @param sourceId the source ID
     * @param sourceType the source type
     * @return the list of notifications
     */
    List<Notification> findBySourceIdAndSourceType(String sourceId, Notification.SourceType sourceType);
} 