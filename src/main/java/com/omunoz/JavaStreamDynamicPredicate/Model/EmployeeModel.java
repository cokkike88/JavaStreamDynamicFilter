package com.omunoz.JavaStreamDynamicPredicate.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeModel {

    private int empId;
    private String empLastName;
    private String empFirstName;
    private DepartmentModel department;
    private List<AddressModel> addresses;
}
