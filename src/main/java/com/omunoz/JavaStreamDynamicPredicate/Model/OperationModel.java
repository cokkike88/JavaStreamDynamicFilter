package com.omunoz.JavaStreamDynamicPredicate.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationModel {
    private String operation;
    private CriteriaModel criteria;
}
