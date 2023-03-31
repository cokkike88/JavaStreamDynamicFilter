package com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor;

import com.omunoz.JavaStreamDynamicPredicate.Model.AddressModel;
import com.omunoz.JavaStreamDynamicPredicate.Model.CriteriaModel;
import com.omunoz.JavaStreamDynamicPredicate.Utils.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Slf4j
public class SpecificationModel<T> {

    private final CriteriaModel criteriaModel;

    public Predicate<T> buildPredicate(){

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(this.criteriaModel.getOperator()))){
            case EQUAL -> {
                return p -> {
                    var root = new RootModel<>(p);
                    if(this.criteriaModel.getKeyField().equals("getAddresses")){
                        return childExecution(p, root);
                    }
                    return root.getProperty(this.criteriaModel.getKeyField()).isPresent() ?
                            root.getProperty(criteriaModel.getKeyField()).get().equals(this.criteriaModel.getListValues().get(0)) : false;
                };

            }
            case CONTAINS -> {
                return p -> {
                    var root = new RootModel<>(p);
                    if(this.criteriaModel.getKeyField().equals("getAddresses")){
                        return childExecution(p, root);
                    }
                    return root.getProperty(this.criteriaModel.getKeyField()).isPresent() ?
                            root.getProperty(criteriaModel.getKeyField()).get().contains(this.criteriaModel.getListValues().get(0)) : false;
                };
            }
            case NOT_EQUAL -> {
                return p -> {
                    var root = new RootModel<>(p);
                    if(this.criteriaModel.getKeyField().equals("getAddresses")){
                        return childExecution(p, root);
                    }
                    return root.getProperty(this.criteriaModel.getKeyField()).isPresent() ?
                            !root.getProperty(criteriaModel.getKeyField()).get().equals(this.criteriaModel.getListValues().get(0)) : false;
                };
            }
            case BETWEEN -> {
                return p -> {
                    var root = new RootModel<>(p);
                    if(this.criteriaModel.getKeyField().equals("getAddresses")){
                        return childExecution(p, root);
                    }
                    return root.getProperty(this.criteriaModel.getKeyField()).isPresent() ?
                            Integer.parseInt(root.getProperty(criteriaModel.getKeyField()).get()) > Integer.parseInt(this.criteriaModel.getListValues().get(0)) &&
                                    Integer.parseInt(root.getProperty(criteriaModel.getKeyField()).get()) < Integer.parseInt(this.criteriaModel.getListValues().get(1)): false;
                };
            }
        }
        return null;
    }

    private Boolean childExecution(T p, RootModel<T> root){
        var c = p.getClass();
        try {
            var method =  c.getMethod("getAddresses").invoke(p);
            var lst = ((List<AddressModel>) method);
            var specificationBuild = new SpecificationBuildModel<AddressModel>();
            this.criteriaModel.getLstCriteria().stream().forEach(x -> {
                if(x.getIsParentValue()){
                    List<String> newValues = x.getListValues().stream().map(nv -> {
                        if(root.getProperty(nv).isPresent()) return root.getProperty(nv).get();
                        return null;
                    }).filter(pn -> pn != null).toList();
                    x.setListValues(newValues);
                }
                x.setIsParentValue(false);
                specificationBuild.with(x);
            });
            var predicate = specificationBuild.build();
            return lst.stream().filter(predicate).count() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
