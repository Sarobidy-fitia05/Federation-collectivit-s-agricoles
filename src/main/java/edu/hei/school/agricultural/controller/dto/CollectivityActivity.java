package edu.hei.school.agricultural.controller.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectivityActivity extends CreateCollectivityActivity {
    private String id;
}