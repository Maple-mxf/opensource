package io.jopen.springboot.plugin.common.tuple;

import lombok.*;

/**
 * @author maxuefeng
 * @since 2019/8/31
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Tuple2<F1, F2> {

    private F1 f1;

    private F2 f2;
}
