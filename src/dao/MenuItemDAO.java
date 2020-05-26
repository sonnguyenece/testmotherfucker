package dao;
import model.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    private static final String select_all_item = "select * from menu";

    public MenuItemDAO() {
    }
    Connection connection = JdbcConnection.getConnection();

    public List<MenuItem> selectAllItem() {
        List<MenuItem> items = new ArrayList<>();

        try (
            PreparedStatement preparedStatement = connection.prepareStatement(select_all_item);
            ) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("menuID");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String descr = rs.getString("descr");
                String img = rs.getString("image");
                String kind = rs.getString("kind");
                items.add(new MenuItem(id, name, price, descr, img, kind));
            }
        } catch (SQLException e) {
           printSQLException(e);
        }
        return items;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
