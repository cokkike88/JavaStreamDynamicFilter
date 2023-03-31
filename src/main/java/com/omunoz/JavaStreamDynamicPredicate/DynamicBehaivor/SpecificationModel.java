package com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor;

import com.omunoz.JavaStreamDynamicPredicate.Model.CriteriaModel;
import com.omunoz.JavaStreamDynamicPredicate.Utils.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Slf4j
public class SpecificationModel<T> {

    private final CriteriaModel criteriaModel;

    public Predicate<RootModel<T>> buildPredicate(){

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(this.criteriaModel.getOperator()))){
            case EQUAL -> {
                return p -> p.getProperty(this.criteriaModel.getKeyField()).isPresent()?
                        p.getProperty(criteriaModel.getKeyField()).get().equals(this.criteriaModel.getValueField()): false;

            }
            case CONTAINS -> {
                return p -> p.getProperty(this.criteriaModel.getKeyField()).isPresent()?
                        p.getProperty(criteriaModel.getKeyField()).get().contains(this.criteriaModel.getValueField()): false;
            }
            case NOT_EQUAL -> {
                return p -> p.getProperty(this.criteriaModel.getKeyField()).isPresent()?
                        !p.getProperty(criteriaModel.getKeyField()).get().equals(this.criteriaModel.getValueField()): false;
            }
        }


        return null;
    }

}
