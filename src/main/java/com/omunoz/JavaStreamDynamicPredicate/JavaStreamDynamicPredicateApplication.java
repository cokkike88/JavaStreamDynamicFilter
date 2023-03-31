package com.omunoz.JavaStreamDynamicPredicate;

import com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor.RootModel;
import com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor.SpecificationBuildModel;
import com.omunoz.JavaStreamDynamicPredicate.Model.AddressModel;
import com.omunoz.JavaStreamDynamicPredicate.Model.CriteriaModel;
import com.omunoz.JavaStreamDynamicPredicate.Model.DepartmentModel;
import com.omunoz.JavaStreamDynamicPredicate.Model.EmployeeModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JavaStreamDynamicPredicateApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaStreamDynamicPredicateApplication.class, args);

		var employees = fillEmployeeList();
		var criteria11 = new CriteriaModel("department.depName", new ArrayList<>(Arrays.asList("MEDIO")), "eq", null, false, null);
		var criteria22 = new CriteriaModel("empLastName", new ArrayList<>(Arrays.asList("kaka")), "eq", "all", false, null);
		var criteria33 = new CriteriaModel("empId", new ArrayList<>(Arrays.asList("2", "8")),"bt", "all", false, null);
//		var criteria44 = new CriteriaModel("getAddresses", new ArrayList<>(Arrays.asList("2")),"eq", "any", false,
//				new ArrayList<>(
//						Arrays.asList(
//								new CriteriaModel("addId", new ArrayList<>(
//										Arrays.asList("1")
//								), "eq", "all", false, null)
//						)
//				));
		var criteria44 = new CriteriaModel("getAddresses", null,"eq", "all", false,
				new ArrayList<>(
						Arrays.asList(
								new CriteriaModel("addId", new ArrayList<>(
										Arrays.asList("empId")
								), "eq", "all", true, null)
						)
				));
		var sb11 = new SpecificationBuildModel<EmployeeModel>();
		sb11.with(criteria11);
		sb11.with(criteria22);
		sb11.with(criteria33);
		sb11.with(criteria44);
		var spEmp = sb11.build();
		var rdEmp = employees.stream().filter(spEmp);
		rdEmp.forEach(p -> {
			System.out.println("****** " + p.getEmpFirstName());
			System.out.println(p.getAddresses());
		});
//		System.out.println("dynamic result -> " + rdEmp.count());
	}

	private static List<EmployeeModel> fillEmployeeList(){
		List<EmployeeModel> lstEmployee = new ArrayList<>(
				Arrays.asList(
						new EmployeeModel(1, "gatuso", "alessandro", new DepartmentModel(1, "MEDIO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(1, "milan", "italy"), new AddressModel(10, "berlin", "germany")))),
						new EmployeeModel(2, "pirlo", "andrea", new DepartmentModel(1, "MEDIO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(2, "turin", "italy")))),
						new EmployeeModel(3, "shevshenko", "andry", new DepartmentModel(1, "DELANTERO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(3, "london", "uk")))),
						new EmployeeModel(4, "pele", "dida", new DepartmentModel(1, "PORTERO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(4, "madrid", "spain")))),
						new EmployeeModel(5, "seedorf", "claren", new DepartmentModel(1, "MEDIO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(5, "paris", "france")))),
						new EmployeeModel(6, "maldini", "paolo", new DepartmentModel(1, "DEFENSA"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(6, "gent", "belgium")))),
						new EmployeeModel(7, "kaka", "ricardo", new DepartmentModel(1, "MEDIO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(7, "segovia", "spain")))),
						new EmployeeModel(8, "nesta", "alesandro", new DepartmentModel(1, "DEFENSA"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(8, "toledo", "spain")))),
						new EmployeeModel(9, "insagi", "pipo", new DepartmentModel(1, "DELANTERO"), new ArrayList<AddressModel>( Arrays.asList( new AddressModel(9, "dublin", "irland"))))
				)
		);
		return lstEmployee;
	}

}
