package com.omunoz.JavaStreamDynamicPredicate.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaModel {
    private String keyField;
    private List<String> listValues;
    private String operator;
    private String dataOption;
    private Boolean isParentValue = false;
    private List<CriteriaModel> lstCriteria;
}
