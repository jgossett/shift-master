package com.joshuagossett.shiftmaster

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface WorkShiftService extends JpaRepository<WorkShift, Long> {
}
