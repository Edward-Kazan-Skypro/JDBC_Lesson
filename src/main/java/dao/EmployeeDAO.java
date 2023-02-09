package dao;

import model.Employee;
import java.util.List;

public interface EmployeeDAO {

    void create(String f_name, String l_name, String gender, int age, int c_id);

    Employee getById(int id);

    List<Employee> getAllEmployees();

    void updateEmployeeById(int id, int city_id);

    void deleteEmployeeById(int id);


}
