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

    public static CourseNumber fromString(String courseNum) {
        for (CourseNumber course : values()) {
            if (course.course.equals(courseNum)) {
                return course;
            }
        }
        return null;
    }
}
