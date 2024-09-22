package dev.check.entity.EnumEntity;

import lombok.Getter;

@Getter
public enum CourseNumber {
    COURSE_1("1"),
    COURSE_2("2"),
    COURSE_3("3");

    private final String course;

    CourseNumber(String course){
        this.course = course;
    }

}
