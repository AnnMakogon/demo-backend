package dev.check.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@lombok.NonNull
public class ParamForGet {
    private int page;
    private int size;
    private String column;
    private String direction;
    private String filter;
    private boolean showFlag;

}
