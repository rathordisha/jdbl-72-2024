package com.example.L08_restful_services_demo.dao;

import com.example.L08_restful_services_demo.entity.Product;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;

@Repository
public class ProductDAOWithDb implements ProductDAOInterface{

    private static Logger LOGGER = LoggerFactory.getLogger(ProductDAO.class);

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Override
    public Product getById(Long id) {
        LOGGER.info("in getById ---------");
        Connection conn =null;
        Product product =null;
        try {
            //1. Register the driver
            //Prerequisite: know the driveer name
            //Not necessary as java automatically loads this driver from the application.properties or pom
            //but a good coding practice to write this step for code readability
            // forName() is a static method of Class which dynamically loads the driver=Registering the driver
            DriverManager.registerDriver(new org.Driver());
            Class.forName("org.postgresql.Driver");

            //2. Get the connection
            conn = DriverManager.getConnection(dbUrl,username,password);

            //3. Get the statement
            String sqlQuery="SELECT id, cost, name FROM public.product where id="+id;
            Statement statement=conn.createStatement();

            //4. Execute the query
            ResultSet rs=statement.executeQuery(sqlQuery);
            while (rs.next()){
                product=new Product(rs.getLong("id"),rs.getString("name"),rs.getDouble("cost"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
           LOGGER.info("inside finally-------");

           //5. Close the connection
            if(conn!=null){
                LOGGER.info("SUCCESS");
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                LOGGER.info("FAIL");
            }
        }
        return product;
    }

    @Override
    public Product create(Product product) {
       // LOGGER.info("in create ---------");
        Connection conn =null;
        try {
            conn = DriverManager.getConnection(dbUrl,username,password);
            //using statement
         //   String sqlQuery1="INSERT INTO public.product VALUES (null,"+product.getCost()+",'"+product.getName()+"');";
          String sqlInsert="insert into public.\"loginDetail\"(cost,name) values(?,?)";
          PreparedStatement preparedStatement=conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);

          preparedStatement.setDouble(1,product.getCost());
          preparedStatement.setString(2,product.getName());
          int affectedRow=preparedStatement.executeUpdate();
          if(affectedRow==0){
              throw new SQLException("Creation failed");
          }
          ResultSet generatedKeys=preparedStatement.getGeneratedKeys();
          while(generatedKeys.next()){
              product.setId(generatedKeys.getLong(1));
          }
           // Statement statement=conn.createStatement();
           // int rs =statement.executeUpdate(sqlQuery1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
           // LOGGER.info("inside finally-------");
            if(conn!=null){
              //  LOGGER.info("SUCCESS");
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
               // LOGGER.info("FAIL");
            }
        }
        return product;
    }

    @Override
    public Product delete(Long id) {
        return null;
    }

    @Override
    public Product update(Long id, Product product) {
        return null;
    }
}
