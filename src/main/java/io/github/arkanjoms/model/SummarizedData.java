package io.github.arkanjoms.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummarizedData implements Serializable {

    private static final long serialVersionUID = 2031991488030573132L;

    private String month;
    private Integer cases;
    private Integer deaths;
}
