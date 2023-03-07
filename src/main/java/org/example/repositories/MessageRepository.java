package org.example.repositories;

import org.example.models.entities.*;
import java.sql.*;
import java.util.*;
import org.example.exceptions.*;

public class MessageRepository {

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
        for ( final MessageEntity entity : entities ) {
            rowsDeleted += delete( entity.getId() );
        }
        
        return rowsDeleted;
    }
    
    
    public int resetSequence() {
    
        int rowsEffected = 0;
    
        try {
            String query = "ALTER TABLE Message AUTO_INCREMENT = 0";
            connection = getConnection();
            statement = connection.prepareStatement( query );
            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
            MessageException ex = new MessageException( sqlException.getMessage() );
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
                MessageException ex = new MessageException( exception.getMessage() );
                throw ex;
            }
        }

        return rowsEffected;
    
    }
    
    
    public int create( MessageEntity record ) throws MessageException {
    
        int rowsEffected = 0;
    
        try {
            String query = "insert into message ( subject, body, sender_id ) values ( ?, ?, ? )";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, record.getSubject());
            statement.setString(2, record.getBody());
            statement.setInt(3, record.getSenderId());

             rowsEffected = statement.executeUpdate();

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public MessageEntity read( Integer id ) throws MessageException {

        MessageEntity item = new MessageEntity();

        try {
            String query = "select id, subject, body, sender_id from message where id = ? ";
            connection = getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            results = statement.executeQuery();
            if(results.next()){
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );
            }
        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return item;
    }

    public List<MessageEntity> read( MessageEntity record ) throws MessageException {

        final List<MessageEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, subject, body, sender_id from message where subject = ? OR body = ? OR sender_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, record.getSubject() );
            statement.setString( 2, record.getBody() );
            statement.setInt( 3, record.getSenderId() );

            results = statement.executeQuery();
            while(results.next()){
            MessageEntity item = new MessageEntity();
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public List<MessageEntity> read() throws MessageException {

        final List<MessageEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, subject, body, sender_id from message ";
            connection = getConnection();
            statement = connection.prepareStatement(query);

            results = statement.executeQuery();
            while(results.next()){
                MessageEntity item = new MessageEntity();
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }

    public int update( Integer id, MessageEntity record ) throws MessageException {

        if( id < 0 ) {
            throw new MessageException( "Message ID is required." ).requiredFields("id");
        }

        if ( record == null ) {
            throw new MessageException( "Message is required." ).nullMessageException();
        }

        int rowsEffected = 0;

        try {
            String query = "update message set   subject  = ?,   body  = ?,   sender_id  = ? where id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, record.getSubject());
            statement.setString(2, record.getBody());
            statement.setInt(3, record.getSenderId());
            statement.setInt( 4, id );

            rowsEffected = statement.executeUpdate();

        } catch ( SQLException sqlException ) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
     MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int delete( Integer id ) throws MessageException {

        int rowsEffected = 0;

        try {
            String query = "delete from message where id = ? ";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            rowsEffected = statement.executeUpdate();
        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }

    public int updateSubject( Integer id, String subject ) throws MessageException {

        if( id < 0 ) {
            throw new MessageException( "Message ID is required." ).requiredFields("id");
        }

        if ( subject == null ) {
            throw new MessageException( "subject is required." ).nullMessageException();
        }

        int rowsEffected = 0;

        try {
            String query = "update message set  subject  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, subject );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }


    public int updateBody( Integer id, String body ) throws MessageException {

        if( id < 0 ) {
            throw new MessageException( "Message ID is required." ).requiredFields("id");
        }

        if ( body == null ) {
            throw new MessageException( "body is required." ).nullMessageException();
        }

        int rowsEffected = 0;

        try {
            String query = "update message set  body  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, body );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }


    public int updateSenderId( Integer id, Integer senderId ) throws MessageException {

        if( id < 0 ) {
            throw new MessageException( "Message ID is required." ).requiredFields("id");
        }

        if ( senderId == null ) {
            throw new MessageException( "senderId is required." ).nullMessageException();
        }

        int rowsEffected = 0;

        try {
            String query = "update message set  sender_id  = ? where id = ?";    
    
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, senderId );
            statement.setInt( 2, id );
    
            rowsEffected = statement.executeUpdate();
    
        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return rowsEffected;
    }



    public boolean existsById( Integer id ) throws MessageException {

        boolean exists = false;
        try {
            String query = "select id, subject, body, sender_id from message where id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, id );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<MessageEntity> searchBySubject( String subject ) throws MessageException {

        final List<MessageEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, subject, body, sender_id from message where subject LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, subject );

            results = statement.executeQuery();
            while( results.next() ) {
            MessageEntity item = new MessageEntity();
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsBySubject( String subject ) throws MessageException {

        boolean exists = false;
        try {
            String query = "select id, subject, body, sender_id from message where subject LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, subject );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<MessageEntity> searchByBody( String body ) throws MessageException {

        final List<MessageEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, subject, body, sender_id from message where body LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, body );

            results = statement.executeQuery();
            while( results.next() ) {
            MessageEntity item = new MessageEntity();
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsByBody( String body ) throws MessageException {

        boolean exists = false;
        try {
            String query = "select id, subject, body, sender_id from message where body LIKE ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setString( 1, body );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }

    public List<MessageEntity> searchBySenderId( Integer senderId ) throws MessageException {

        final List<MessageEntity> itemList = new ArrayList<>();

        try {
            String query = "select id, subject, body, sender_id from message where sender_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, senderId );

            results = statement.executeQuery();
            while( results.next() ) {
            MessageEntity item = new MessageEntity();
                item.setId( results.getInt("id") );
                item.setSubject( results.getString("subject") );
                item.setBody( results.getString("body") );
                item.setSenderId( results.getInt("sender_id") );

                itemList.add(item);
            }

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return itemList;
    }


    public boolean existsBySenderId( Integer senderId ) throws MessageException {

        boolean exists = false;
        try {
            String query = "select id, subject, body, sender_id from message where sender_id = ?";
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt( 1, senderId );

            results = statement.executeQuery();
            exists = results.next();

        } catch (SQLException sqlException) {
    MessageException ex = new MessageException(sqlException.getMessage());
            throw ex;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception exception) {
        MessageException ex = new MessageException(exception.getMessage());
                     throw ex;
            }
        }

        return exists;
    }


}
