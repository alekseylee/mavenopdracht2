package org.example.repositories;

import org.example.models.entities.*;
import java.sql.*;
import java.util.*;
import org.example.exceptions.*;

public class ShareRepository {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet results = null;
    
    private Connection getConnection() throws SQLException {
            Connection conn;
            conn = ConnectionFactory.getInstance().getConnection();
            return conn;
    }

    
    public int resetSequence() {
    
        int rowsEffected = 0;
    
        try {
            String query = "ALTER TABLE Share AUTO_INCREMENT = 0";
            connection = getConnection();
            statement = connection.prepareStatement( query );
            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
            ShareException ex = new ShareException( sqlException.getMessage() );
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
                ShareException ex = new ShareException( exception.getMessage() );
                throw ex;
            }
        }

        return rowsEffected;
    
    }
    
    
    public int create( ShareEntity record ) throws ShareException {
    
        int rowsEffected = 0;
    
        try {
            String query = "insert into share ( receiver_id ) values ( ? )";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, record.getReceiverId());

             rowsEffected = statement.executeUpdate();

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public ShareEntity read( Integer id ) throws ShareException {

        ShareEntity item = new ShareEntity();

        try {
            String query = "select message_id, receiver_id from share where id = ? ";
            connection = getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            results = statement.executeQuery();
            if(results.next()){
                item.setMessageId( results.getInt("message_id") );
                item.setReceiverId( results.getInt("receiver_id") );
            }
        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return item;
    }

    public List<ShareEntity> read( ShareEntity record ) throws ShareException {

        final List<ShareEntity> itemList = new ArrayList<>();

        try {
            String query = "select message_id, receiver_id from share where receiver_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, record.getReceiverId() );

            results = statement.executeQuery();
            while(results.next()){
            ShareEntity item = new ShareEntity();
                item.setMessageId( results.getInt("message_id") );
                item.setReceiverId( results.getInt("receiver_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public List<ShareEntity> read() throws ShareException {

        final List<ShareEntity> itemList = new ArrayList<>();

        try {
            String query = "select message_id, receiver_id from share ";
            connection = getConnection();
            statement = connection.prepareStatement(query);

            results = statement.executeQuery();
            while(results.next()){
                ShareEntity item = new ShareEntity();
                item.setMessageId( results.getInt("message_id") );
                item.setReceiverId( results.getInt("receiver_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public int update( Integer id, ShareEntity record ) throws ShareException {

        if( id < 0 ) {
            throw new ShareException( "Share ID is required." ).requiredFields("id");
        }

        if ( record == null ) {
            throw new ShareException( "Share is required." ).nullShareException();
        }

        int rowsEffected = 0;

        try {
            String query = "update share set   receiver_id  = ? where id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, record.getReceiverId());
            statement.setInt( 2, id );

            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
     ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int delete( Integer id ) throws ShareException {

        int rowsEffected = 0;

        try {
            String query = "delete from share where id = ? ";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            rowsEffected = statement.executeUpdate();
        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int updateReceiverId( Integer id, Integer receiverId ) throws ShareException {

        if( id < 0 ) {
            throw new ShareException( "Share ID is required." ).requiredFields("id");
        }

        if ( receiverId == null ) {
            throw new ShareException( "receiverId is required." ).nullShareException();
        }

        int rowsEffected = 0;

        try {
            String query = "update share set  receiver_id  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, receiverId );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }



    public boolean existsByMessageId( Integer messageId ) throws ShareException {

        boolean exists = false;
        try {
            String query = "select message_id, receiver_id from share where message_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, messageId );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<ShareEntity> searchByReceiverId( Integer receiverId ) throws ShareException {

        final List<ShareEntity> itemList = new ArrayList<>();

        try {
            String query = "select message_id, receiver_id from share where receiver_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, receiverId );

            results = statement.executeQuery();
            while( results.next() ) {
            ShareEntity item = new ShareEntity();
                item.setMessageId( results.getInt("message_id") );
                item.setReceiverId( results.getInt("receiver_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsByReceiverId( Integer receiverId ) throws ShareException {

        boolean exists = false;
        try {
            String query = "select message_id, receiver_id from share where receiver_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, receiverId );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    ShareException ex = new ShareException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        ShareException ex = new ShareException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }


}
