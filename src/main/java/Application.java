import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import model.Employee;
import java.sql.*;
import java.util.List;

public class Application {


    public static void main(String[] args) throws SQLException {

        final String user = "postgres";
        final String pass = "admin";
        final String url = "jdbc:postgresql://localhost:5432/skypro";
        try (final Connection connection = DriverManager.getConnection(url, user, pass)) {

            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

            //Добавление в БД нового employee
            employeeDAO.create("TEST_FIRST_NAME", "TEST_LAST_NAME", "male", 44, 3);

            //Получение из БД employee по его id
            System.out.println(employeeDAO.getById(2));

            //Получение из БД всего списка employee
            List<Employee> employees = employeeDAO.getAllEmployees();
            for (Employee employee : employees) {
                System.out.println(employee);
            }

            //Обновление employee с id=2 - меняем id города-проживания
            employeeDAO.updateEmployeeById(2, 4);

            //Удаление employee с id=10
            employeeDAO.deleteEmployeeById(10);
        }
    }
}
