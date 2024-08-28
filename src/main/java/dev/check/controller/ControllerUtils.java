package dev.check.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ControllerUtils {
    public static Pageable createPageable(int page, int size, String column, String direction){

        Sort sort = Objects.equals(direction, "") ? Sort.by(column) : Sort.by(Sort.Direction.fromString(direction), column);//если не указано направление, то сортируем по fio

        return PageRequest.of(page, size, sort);
    }
}
