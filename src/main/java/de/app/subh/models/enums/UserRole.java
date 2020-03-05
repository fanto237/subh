package de.app.subh.models.enums;

public enum UserRole {
    STUDENT, // can not borrow more than 5 books at the same time
    NORMAL, // // can not borrow more than 3 books at the same times
    PROF, // can borrow more books as he wants to, than he has no limit
    ADMIN // same as a PROF User but can also manage the library system
}
