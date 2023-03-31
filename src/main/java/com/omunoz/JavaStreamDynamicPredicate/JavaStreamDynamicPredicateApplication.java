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

		// ===============================
		System.out.println("==================================");
		var rootList = fillRootEmployees();
		var criteria1 = new CriteriaModel("department.depName", "MEDIO", "eq", null, false, null);
		var criteria2 = new CriteriaModel("empLastName", "kaka", "eq", "all", false, null);
		var sb = new SpecificationBuildModel<EmployeeModel>();
		sb.with(criteria1);
		sb.with(criteria2);
		var pd = sb.build();
		var rd = rootList.stream().filter(pd).count();
		System.out.println("dynamic result -> " + rd);

		try {
			var method = EmployeeModel.class.getMethod("getAddresses").invoke(rootList.get(0).getRoot());
			if(method == null){
				System.out.println("null");
			}
			else {
//				var lst = ((List<?>) method);
//				List<? extends RootModel<?>> newList = lst.stream().map(p -> new RootModel<>(p)).toList();
//
//				var sb2 = new SpecificationBuildModel<AddressModel>();
//				sb2.with(new CriteriaModel("addLocation", "germany", "eq", null));
//				Predicate<? extends RootModel<?>> sbP = sb2.build();
//
////				var res = newList.stream().filter(p -> p.getProperty("addLocation").get().equals("germany"));
//				var res = newList.stream().filter(sbP);
//				System.out.println("address size -> " + res);

				var lst = ((List<AddressModel>) method);
				List<RootModel<AddressModel>> newList = lst.stream().map(p -> new RootModel<>(p)).toList();

				var sb2 = new SpecificationBuildModel<AddressModel>();
				sb2.with(new CriteriaModel("addLocation", "germany", "eq", null, false, null));
				var sbP = sb2.build();

//				var res = newList.stream().filter(p -> p.getProperty("addLocation").get().equals("germany"));
				var res = newList.stream().filter(sbP);
				System.out.println("address size -> " + res.count());


				System.out.println("size -> " + lst.size() + "  " + newList.get(0));
			}
		}
		catch (Exception ex){
			System.err.println("error invoke the method ->" + ex.getMessage());
		}
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

	private static List<RootModel<EmployeeModel>> fillRootEmployees(){
		List<RootModel<EmployeeModel>> lstEmployee = fillEmployeeList().stream().map(p -> new RootModel<>(p)).toList();
		return lstEmployee;
	}

}
