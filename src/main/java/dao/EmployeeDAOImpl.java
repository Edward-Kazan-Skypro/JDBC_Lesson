package dao;

import model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(String f_name, String l_name, String gender, int age, int c_id) {

        try (PreparedStatement statement = connection.prepareStatement(Queries.INSERT.query)){
            statement.setString(1, f_name);
            statement.setString(2, l_name);
            statement.setString(3, gender);
            statement.setInt(4, age);
            statement.setInt(5, c_id);
            statement.executeQuery();
        } catch (SQLException e) {
        }
    }

    @Override
    public Employee getById(int id) {
        Employee employee = new Employee();
        try (PreparedStatement statement = connection.prepareStatement(Queries.GET.query)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                employee.setId(resultSet.getInt("id"));
                employee.setFirst_name(resultSet.getString("first_name"));
                employee.setLast_name(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(resultSet.getInt("age"));
                employee.setCity_id(resultSet.getInt("city_id"));
            }
        } catch (SQLException e) {
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(Queries. GET_ALL.query)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                int city_id = resultSet.getInt("city_id");

                employees.add(new Employee(id, first_name, last_name, gender, age, city_id));
            }
        } catch (SQLException e) {
        }
        return employees;
    }

    //Допустим сотрудник переехал и мы обновляем город, где он живет
    //Другие поля наверно лучше не трогать
    //Возраст можно настроить чтоб сам на 1 увеличивался каждый год,
    //А имя и фамилия меняется достаточно редко
    //Ну и смена пола тоже достаточно редкая операция
    @Override
    public void updateEmployeeById(int id, int city_id) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.UPDATE.query)){
            statement.setInt(1, city_id);
            statement.setInt(2, id);
            statement.executeQuery();
        } catch (SQLException e) {
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.DELETE.query)){
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
        }
    }
}

enum Queries {

    GET ("SELECT * FROM employee INNER JOIN city ON employee.id = city.city_id AND employee.id = (?)"),
    GET_ALL ("SELECT * FROM employee"),
    INSERT("INSERT INTO employee (first_name, last_name, gender, age, city_id) VALUES ((?), (?), (?), (?), (?))"),
    DELETE("DELETE FROM employee WHERE id = (?)"),
    UPDATE("UPDATE employee SET city_id = (?) WHERE id = (?)");

    String query;

    Queries(String query) {
        this.query = query;
    }
}
