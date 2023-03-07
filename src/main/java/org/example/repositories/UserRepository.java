package org.example.repositories;

import org.example.models.entities.*;
import java.sql.*;
import java.util.*;
import org.example.exceptions.*;

public class UserRepository {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet results = null;
    
    private Connection getConnection() throws SQLException {
            Connection conn;
            conn = ConnectionFactory.getInstance().getConnection();
            return conn;
    }
    
    public int clear() {

    int rowsDeleted = 0;
    final var entities = read();
        for ( final UserEntity entity : entities ) {
            rowsDeleted += delete( entity.getId() );
        }
        
        return rowsDeleted;
    }
    
    
    public int resetSequence() {
    
        int rowsEffected = 0;
    
        try {
            String query = "ALTER TABLE User AUTO_INCREMENT = 0";
            connection = getConnection();
            statement = connection.prepareStatement( query );
            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
            UserException ex = new UserException( sqlException.getMessage() );
            throw ex;
        } finally {
            try {
                if ( statement != null ) {
                    statement.close();
                }
                if ( connection != null ) {
                    connection.close();
                }
            } catch ( Exception exception ) {
                UserException ex = new UserException( exception.getMessage() );
                throw ex;
            }
        }

        return rowsEffected;
    
    }
    
    
    public int create( UserEntity record ) throws UserException {
    
        int rowsEffected = 0;
    
        try {
            String query = "insert into user ( email, passcode, active ) values ( ?, ?, ? )";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPasscode());
            statement.setInt(3, record.getActive());

             rowsEffected = statement.executeUpdate();

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public UserEntity read( Integer id ) throws UserException {

        UserEntity item = new UserEntity();

        try {
            String query = "select id, email, passcode, active from user where id = ? ";
            connection = getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            results = statement.executeQuery();
            if(results.next()){
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );
            }
        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return item;
    }

    public List<UserEntity> read( UserEntity record ) throws UserException {

        final List<UserEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, email, passcode, active from user where email = ? OR passcode = ? OR active = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, record.getEmail() );
            statement.setString( 2, record.getPasscode() );
            statement.setInt( 3, record.getActive() );

            results = statement.executeQuery();
            while(results.next()){
            UserEntity item = new UserEntity();
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public List<UserEntity> read() throws UserException {

        final List<UserEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, email, passcode, active from user ";
            connection = getConnection();
            statement = connection.prepareStatement(query);

            results = statement.executeQuery();
            while(results.next()){
                UserEntity item = new UserEntity();
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public int update( Integer id, UserEntity record ) throws UserException {

        if( id < 0 ) {
            throw new UserException( "User ID is required." ).requiredFields("id");
        }

        if ( record == null ) {
            throw new UserException( "User is required." ).nullUserException();
        }

        int rowsEffected = 0;

        try {
            String query = "update user set   email  = ?,   passcode  = ?,   active  = ? where id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPasscode());
            statement.setInt(3, record.getActive());
            statement.setInt( 4, id );

            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
     UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int delete( Integer id ) throws UserException {

        int rowsEffected = 0;

        try {
            String query = "delete from user where id = ? ";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            rowsEffected = statement.executeUpdate();
        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int updateEmail( Integer id, String email ) throws UserException {

        if( id < 0 ) {
            throw new UserException( "User ID is required." ).requiredFields("id");
        }

        if ( email == null ) {
            throw new UserException( "email is required." ).nullUserException();
        }

        int rowsEffected = 0;

        try {
            String query = "update user set  email  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, email );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }


    public int updatePasscode( Integer id, String passcode ) throws UserException {

        if( id < 0 ) {
            throw new UserException( "User ID is required." ).requiredFields("id");
        }

        if ( passcode == null ) {
            throw new UserException( "passcode is required." ).nullUserException();
        }

        int rowsEffected = 0;

        try {
            String query = "update user set  passcode  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, passcode );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }


    public int updateActive( Integer id, Integer active ) throws UserException {

        if( id < 0 ) {
            throw new UserException( "User ID is required." ).requiredFields("id");
        }

        if ( active == null ) {
            throw new UserException( "active is required." ).nullUserException();
        }

        int rowsEffected = 0;

        try {
            String query = "update user set  active  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, active );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }



    public boolean existsById( Integer id ) throws UserException {

        boolean exists = false;
        try {
            String query = "select id, email, passcode, active from user where id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, id );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<UserEntity> searchByEmail( String email ) throws UserException {

        final List<UserEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, email, passcode, active from user where email LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, email );

            results = statement.executeQuery();
            while( results.next() ) {
            UserEntity item = new UserEntity();
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsByEmail( String email ) throws UserException {

        boolean exists = false;
        try {
            String query = "select id, email, passcode, active from user where email LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, email );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<UserEntity> searchByPasscode( String passcode ) throws UserException {

        final List<UserEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, email, passcode, active from user where passcode LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, passcode );

            results = statement.executeQuery();
            while( results.next() ) {
            UserEntity item = new UserEntity();
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsByPasscode( String passcode ) throws UserException {

        boolean exists = false;
        try {
            String query = "select id, email, passcode, active from user where passcode LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, passcode );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<UserEntity> searchByActive( Integer active ) throws UserException {

        final List<UserEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, email, passcode, active from user where active = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, active );

            results = statement.executeQuery();
            while( results.next() ) {
            UserEntity item = new UserEntity();
                item.setId( results.getInt("id") );
                item.setEmail( results.getString("email") );
                item.setPasscode( results.getString("passcode") );
                item.setActive( results.getInt("active") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsByActive( Integer active ) throws UserException {

        boolean exists = false;
        try {
            String query = "select id, email, passcode, active from user where active = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, active );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    UserException ex = new UserException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        UserException ex = new UserException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }


}
