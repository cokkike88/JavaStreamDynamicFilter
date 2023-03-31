package com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor;

import com.omunoz.JavaStreamDynamicPredicate.Model.CriteriaModel;
import com.omunoz.JavaStreamDynamicPredicate.Utils.SearchOperation;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class SpecificationBuildModel<T> {

    private List<CriteriaModel> params;

    public SpecificationBuildModel () {
        this.params = new ArrayList<>();
    }

    public void with(CriteriaModel criteriaModel){
        this.params.add(criteriaModel);
    }

    public Predicate<RootModel<T>> build(){
        if (params.size() == 0) return null;

        var specificationModel = new SpecificationModel<T>(this.params.get(0));
        Predicate<RootModel<T>> predicate = specificationModel.buildPredicate();

        for (int i = 1; i < this.params.size(); i++){
            var criteria = this.params.get(i);
            var sm = new SpecificationModel<T>(criteria);

            predicate = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL?
                    predicate.and(sm.buildPredicate()):
                    predicate.or(sm.buildPredicate());
        }

        return predicate;

    }


}

