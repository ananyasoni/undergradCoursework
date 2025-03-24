package flightapp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Runs queries against a back-end database
 */
public class Query extends QueryAbstract {
  //
  // Canned queries
  //
  private static final String FLIGHT_CAPACITY_SQL = "SELECT capacity FROM Flights WHERE fid = ?";
  private static final String FLIGHT_AMOUNT_SQL = "SELECT count(username) AS num_reserved FROM Reservations_ananya99 WHERE f1_id = ? OR f2_id = ?";
  private static final String MAXIMUM_RESERVATION_ID_SQL = "SELECT COALESCE(MAX(reservation_id), 0) AS max_id FROM Reservations_ananya99";
  private static final String LOGIN_SQL = "SELECT username, password FROM Users_ananya99 WHERE LOWER(username) = ?";
  private static final String CHECK_USER_SQL = "SELECT username FROM Users_ananya99 WHERE LOWER(username) = ?";
  private static final String INSERT_USER_SQL = "INSERT INTO Users_ananya99 (username, password, balance) VALUES (?, ?, ?)";
  // Query for direct flights (non-canceled flights)
  private static final String DIRECT_FLIGHT_SQL = "SELECT fid, day_of_month, carrier_id, flight_num, origin_city, dest_city, actual_time, capacity, price FROM Flights WHERE origin_city = ? AND dest_city = ? AND day_of_month = ? AND canceled = 0 ORDER BY actual_time ASC, fid ASC LIMIT ?";
  private static final String INDIRECT_FLIGHT_SQL = "SELECT f1.fid as fid1, f1.day_of_month as day1, f1.carrier_id as carrier1, f1.flight_num as flight_num1, f1.origin_city as origin1, f1.dest_city as dest1, f1.actual_time as time1, f1.capacity as capacity1, f1.price as price1, f2.fid as fid2, f2.day_of_month as day2, f2.carrier_id as carrier2, f2.flight_num as flight_num2, f2.origin_city as origin2, f2.dest_city as dest2, f2.actual_time as time2, f2.capacity as capacity2, f2.price as price2 FROM Flights f1, Flights f2 WHERE f1.origin_city = ? AND f1.dest_city = f2.origin_city AND f2.dest_city = ? AND f1.day_of_month = ? AND f2.day_of_month = ? AND f1.canceled = 0 AND f2.canceled = 0 AND f1.month_id = f2.month_id AND f1.day_of_week_id = f2.day_of_week_id ORDER BY (f1.actual_time + f2.actual_time) ASC, f1.fid ASC, f2.fid ASC LIMIT ?";
  private static final String BOOK_INDIRECT_SQL = "INSERT INTO Reservations_ananya99 (reservation_id, username, f1_id, f2_id, is_paid, is_direct, day_of_month, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String BOOK_DIRECT_SQL = "INSERT INTO Reservations_ananya99 (reservation_id, username, f1_id, f2_id, is_paid, is_direct, day_of_month, total_amount) VALUES (?, ?, ?, NULL, ?, ?, ?, ?)";
  private static final String SAME_DAY_CHECK_SQL = "SELECT * FROM Reservations_ananya99 WHERE username = ? AND day_of_month = ?";
  private static final String FIND_RESERVATION_SQL = "SELECT * FROM Reservations_ananya99 WHERE reservation_id = ? AND username = ? AND is_paid = false";
  private static final String GET_USER_BALANCE_SQL = "SELECT balance FROM Users_ananya99 WHERE username = ?";
  private static final String UPDATE_USER_BALANCE_SQL = "UPDATE Users_ananya99 SET balance = balance - ? WHERE username = ?";
  private static final String UPDATE_RESERVATION_SQL = "UPDATE Reservations_ananya99 SET is_paid = true WHERE reservation_id = ?";
  private static final String GET_RESERVATIONS_SQL = "SELECT r.reservation_id, r.is_paid, r.f1_id, r.f2_id, f1.day_of_month as f1_day, f1.carrier_id as f1_carrier, f1.flight_num as f1_num, f1.origin_city as f1_origin, f1.dest_city as f1_dest, f1.actual_time as f1_time, f1.capacity as f1_capacity, f1.price as f1_price, f2.day_of_month as f2_day, f2.carrier_id as f2_carrier, f2.flight_num as f2_num, f2.origin_city as f2_origin, f2.dest_city as f2_dest, f2.actual_time as f2_time, f2.capacity as f2_capacity, f2.price as f2_price FROM Reservations_ananya99 r JOIN Flights f1 ON r.f1_id = f1.fid LEFT JOIN Flights f2 ON r.f2_id = f2.fid WHERE r.username = ? ORDER BY r.reservation_id ASC";
  private PreparedStatement getReservationsStmt;
  private PreparedStatement updateReservationStmt;
  private PreparedStatement updateUserBalanceStmt;
  private PreparedStatement getUserBalanceStmt;
  private PreparedStatement findReservationStmt;
  private PreparedStatement sameDayCheckStmt;
  private PreparedStatement indirectFlightStmt;
  private PreparedStatement directFlightStmt;
  private PreparedStatement insertUserStmt;
  private PreparedStatement loginStmt;
  private PreparedStatement flightCapacityStmt;
  private PreparedStatement flightAmountStmt;
  private PreparedStatement maximumReservationIDStmt;
  private PreparedStatement checkUserStmt;
  private PreparedStatement bookIndirectStmt;
  private PreparedStatement bookDirectStmt;

  //
  // Instance variables
  //

  // keeps track of the currentUser
  // null if no user currently logged in otherwise
  // current user's username
  private String currentUser = null;
  // list of itineraries from the most recent search
  // in the current session
  List<Itinerary> finalItineraries = new ArrayList<>();

  protected Query() throws SQLException, IOException {
    prepareStatements();
  }

  /**
   * Clear the data in any custom tables created.
   * WARNING! Do not drop any tables and do not clear the flights table.
   */
  public void clearTables() {
    try (Statement stmt = conn.createStatement()) {
      stmt.executeUpdate("DELETE FROM Reservations_ananya99");
      stmt.executeUpdate("DELETE FROM Users_ananya99");
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  /*
   * prepare all the SQL statements in this method.
   */
  private void prepareStatements() throws SQLException {
    flightCapacityStmt = conn.prepareStatement(FLIGHT_CAPACITY_SQL);
    flightAmountStmt = conn.prepareStatement(FLIGHT_AMOUNT_SQL);
    maximumReservationIDStmt = conn.prepareStatement(MAXIMUM_RESERVATION_ID_SQL);
    loginStmt = conn.prepareStatement(LOGIN_SQL);
    checkUserStmt = conn.prepareStatement(CHECK_USER_SQL);
    insertUserStmt = conn.prepareStatement(INSERT_USER_SQL);
    directFlightStmt = conn.prepareStatement(DIRECT_FLIGHT_SQL);
    indirectFlightStmt = conn.prepareStatement(INDIRECT_FLIGHT_SQL);
    bookIndirectStmt = conn.prepareStatement(BOOK_INDIRECT_SQL);
    bookDirectStmt = conn.prepareStatement(BOOK_DIRECT_SQL);
    sameDayCheckStmt = conn.prepareStatement(SAME_DAY_CHECK_SQL);
    findReservationStmt = conn.prepareStatement(FIND_RESERVATION_SQL);
    getUserBalanceStmt = conn.prepareStatement(GET_USER_BALANCE_SQL);
    updateUserBalanceStmt = conn.prepareStatement(UPDATE_USER_BALANCE_SQL);
    updateReservationStmt = conn.prepareStatement(UPDATE_RESERVATION_SQL);
    getReservationsStmt = conn.prepareStatement(GET_RESERVATIONS_SQL);
  }

  /* See QueryAbstract.java for javadoc */
  public String transaction_login(String username, String password) {
    if (currentUser != null) {
      return "User already logged in\n";
    }
    try {
      loginStmt.clearParameters();
      loginStmt.setString(1, username.toLowerCase());
      ResultSet userResult = loginStmt.executeQuery();
      if (userResult.next()) {
          String storedUsername = userResult.getString("username");
          byte[] storedPassword = userResult.getBytes("password");
          if (PasswordUtils.plaintextMatchesSaltedHash(password, storedPassword)) {
              currentUser = storedUsername;
              userResult.close();
              this.finalItineraries.clear();
              return "Logged in as " + storedUsername + "\n";
          }
      }
      userResult.close();
      return "Login failed\n";
    } catch (SQLException e) {
      e.printStackTrace();
      return "Login failed\n";
    }
  }
  /* See QueryAbstract.java for javadoc */
  public String transaction_createCustomer(String username, String password, int initAmount) {
    if (username == null || password == null || initAmount < 0) {
        return "Failed to create user\n";
    }
    try {
      conn.setAutoCommit(false);
      String lowercaseUsername = username.toLowerCase();
      checkUserStmt.clearParameters();
      checkUserStmt.setString(1, lowercaseUsername);
      ResultSet userResult = checkUserStmt.executeQuery();
      if (userResult.next()) {
          userResult.close();
          conn.rollback();
          conn.setAutoCommit(true);
          return "Failed to create user\n";
      }
      userResult.close();
      byte[] saltedHashPassword = PasswordUtils.saltAndHashPassword(password);
      insertUserStmt.clearParameters();
      insertUserStmt.setString(1, username);
      insertUserStmt.setBytes(2, saltedHashPassword);
      insertUserStmt.setInt(3, initAmount);
      insertUserStmt.executeUpdate();
      conn.commit();
      conn.setAutoCommit(true);
      return "Created user " + username + "\n";
    } catch (SQLException e) {
        try {
          conn.rollback();
          conn.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (isDeadlock(e)) {
            return transaction_createCustomer(username, password, initAmount);
        }
        e.printStackTrace();
        return "Failed to create user\n";
    }
  }
  /* See QueryAbstract.java for javadoc */
  public String transaction_search(String originCity, String destinationCity, boolean directFlight, int dayOfMonth, int numberOfItineraries) {
    StringBuilder sb = new StringBuilder();
    try {
      List<Itinerary> directItineraries = new ArrayList<>();
      List<Itinerary> indirectItineraries = new ArrayList<>();
      directFlightStmt.clearParameters();
      directFlightStmt.setString(1, originCity);
      directFlightStmt.setString(2, destinationCity);
      directFlightStmt.setInt(3, dayOfMonth);
      directFlightStmt.setInt(4, numberOfItineraries);
      ResultSet directResults = directFlightStmt.executeQuery();
      while (directResults.next()) {
        int fid = directResults.getInt("fid");
        int day = directResults.getInt("day_of_month");
        String carrier = directResults.getString("carrier_id");
        String flightNum = directResults.getString("flight_num");
        String origin = directResults.getString("origin_city");
        String dest = directResults.getString("dest_city");
        int duration = directResults.getInt("actual_time");
        int capacity = directResults.getInt("capacity");
        int price = directResults.getInt("price");
        Flight flight = new Flight(fid, day, carrier, flightNum, origin, dest, duration, capacity, price);
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Itinerary itinerary = new Itinerary(flights, duration);
        directItineraries.add(itinerary);
      }
      directResults.close();
      // If not direct-only search, find indirect flights that are not cancelled and on the same day
      if (!directFlight) {
        indirectFlightStmt.clearParameters();
        indirectFlightStmt.setString(1, originCity);
        indirectFlightStmt.setString(2, destinationCity);
        indirectFlightStmt.setInt(3, dayOfMonth);
        indirectFlightStmt.setInt(4, dayOfMonth);
        indirectFlightStmt.setInt(5, numberOfItineraries);
        ResultSet indirectResults = indirectFlightStmt.executeQuery();
        while (indirectResults.next()) {
          int fid1 = indirectResults.getInt("fid1");
          int day1 = indirectResults.getInt("day1");
          String carrier1 = indirectResults.getString("carrier1");
          String flightNum1 = indirectResults.getString("flight_num1");
          String origin1 = indirectResults.getString("origin1");
          String dest1 = indirectResults.getString("dest1");
          int duration1 = indirectResults.getInt("time1");
          int capacity1 = indirectResults.getInt("capacity1");
          int price1 = indirectResults.getInt("price1");
          int fid2 = indirectResults.getInt("fid2");
          int day2 = indirectResults.getInt("day2");
          String carrier2 = indirectResults.getString("carrier2");
          String flightNum2 = indirectResults.getString("flight_num2");
          String origin2 = indirectResults.getString("origin2");
          String dest2 = indirectResults.getString("dest2");
          int duration2 = indirectResults.getInt("time2");
          int capacity2 = indirectResults.getInt("capacity2");
          int price2 = indirectResults.getInt("price2");
          Flight flight1 = new Flight(fid1, day1, carrier1, flightNum1, origin1, dest1, duration1, capacity1, price1);
          Flight flight2 = new Flight(fid2, day2, carrier2, flightNum2, origin2, dest2, duration2, capacity2, price2);
          List<Flight> flights = new ArrayList<>();
          flights.add(flight1);
          flights.add(flight2);
          int totalDuration = duration1 + duration2;
          Itinerary itinerary = new Itinerary(flights, totalDuration);
          indirectItineraries.add(itinerary);
        }
        indirectResults.close();
      }
      if (directItineraries.isEmpty() && indirectItineraries.isEmpty()) {
        this.finalItineraries.clear();
        return "No flights match your selection\n";
      }
      int directCount = Math.min(directItineraries.size(), numberOfItineraries);
      int indirectCount = 0;
      if (!directFlight && directCount < numberOfItineraries) {
        indirectCount = Math.min(indirectItineraries.size(), numberOfItineraries - directCount);
      }
      this.finalItineraries.clear();
      if (directCount > 0) {
        finalItineraries.addAll(directItineraries.subList(0, directCount));
      }
      if (indirectCount > 0) {
        finalItineraries.addAll(indirectItineraries.subList(0, indirectCount));
        Collections.sort(finalItineraries);
      }
      // Format output
      for (int i = 0; i < finalItineraries.size(); i++) {
        Itinerary itinerary = finalItineraries.get(i);
        itinerary.setItineraryId(i);
        sb.append(itinerary.toString());
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return "Failed to search\n";
    }
    return sb.toString();
  }
  /* See QueryAbstract.java for javadoc */
  public String transaction_book(int itineraryId) {
    // user not logged in case
    if (currentUser == null) {
        return "Cannot book reservations, not logged in\n";
    }
    // invalid itinerary id or search never completed case
    if (itineraryId < 0 || itineraryId >= this.finalItineraries.size() || this.finalItineraries.isEmpty()) {
        return "No such itinerary " + itineraryId + "\n";
    }
    try {
      conn.setAutoCommit(false);
      // check if the user already has a reservation on the same day as the one they're trying to book
      int dayOfMonth = this.finalItineraries.get(itineraryId).flights.get(0).dayOfMonth;
      sameDayCheckStmt.clearParameters();
      sameDayCheckStmt.setString(1, currentUser);
      sameDayCheckStmt.setInt(2, dayOfMonth);
      ResultSet sameDayCheckResult = sameDayCheckStmt.executeQuery();
      if (sameDayCheckResult.next()) {
          sameDayCheckResult.close();
          sameDayCheckStmt.close();
          conn.rollback();
          conn.setAutoCommit(true);
          return "You cannot book two flights in the same day\n";
      }
      sameDayCheckResult.close();
      // check if flight(s) maximum capacity would be exceeded by booking
      int f1_id = this.finalItineraries.get(itineraryId).flights.get(0).fid;
      if (checkFlightAmountReservations(f1_id) >= checkFlightCapacity(f1_id)) {
          conn.rollback();
          conn.setAutoCommit(true);
          return "Booking failed\n";
      }
      Integer f2_id = null;
      if (this.finalItineraries.get(itineraryId).flights.size() == 2) {
          f2_id = this.finalItineraries.get(itineraryId).flights.get(1).fid;
          if (checkFlightAmountReservations(f2_id) >= checkFlightCapacity(f2_id)) {
              conn.rollback();
              conn.setAutoCommit(true);
              return "Booking failed\n";
          }
      }
      int reservationId = nextReservationID();
      // If we don't fall into any of the above cases we can safely book the flight
      int result;
      if (f2_id != null) {
          // indirect itinerary reservation
          bookIndirectStmt.clearParameters();
          bookIndirectStmt.setInt(1, reservationId);
          bookIndirectStmt.setString(2, currentUser);
          bookIndirectStmt.setInt(3, f1_id);
          bookIndirectStmt.setInt(4, f2_id);
          bookIndirectStmt.setBoolean(5, false);
          bookIndirectStmt.setBoolean(6, false);
          bookIndirectStmt.setInt(7, dayOfMonth);
          bookIndirectStmt.setInt(8, this.finalItineraries.get(itineraryId).totalPrice);
          result = bookIndirectStmt.executeUpdate();
      } else {
          // direct itinerary reservation
          bookDirectStmt.clearParameters();
          bookDirectStmt.setInt(1, reservationId);
          bookDirectStmt.setString(2, currentUser);
          bookDirectStmt.setInt(3, f1_id);
          bookDirectStmt.setBoolean(4, false);
          bookDirectStmt.setBoolean(5, true);
          bookDirectStmt.setInt(6, dayOfMonth);
          bookDirectStmt.setInt(7, this.finalItineraries.get(itineraryId).totalPrice);
          result = bookDirectStmt.executeUpdate();
      }
      if (result != 1) {
          conn.rollback();
          conn.setAutoCommit(true);
          return "Booking failed\n";
      }
      conn.commit();
      conn.setAutoCommit(true);
      return "Booked flight(s), reservation ID: " + reservationId + "\n";
    } catch (SQLException e) {
        try {
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (isDeadlock(e)) {
            return transaction_book(itineraryId);
        }
        e.printStackTrace();
        return "Booking failed\n";
    }
  }

  /* See QueryAbstract.java for javadoc */
  public String transaction_pay(int reservationId) {
    // check if user is logged in
    if (currentUser == null) {
      return "Cannot pay, not logged in\n";
    }
    try {
        conn.setAutoCommit(false);
        // find the unpaid reservation for the current user
        findReservationStmt.clearParameters();
        findReservationStmt.setInt(1, reservationId);
        findReservationStmt.setString(2, currentUser);
        ResultSet reservationResult = findReservationStmt.executeQuery();
        // check if reservation exists
        if (!reservationResult.next()) {
            reservationResult.close();
            conn.rollback();
            conn.setAutoCommit(true);
            return "Cannot find unpaid reservation " + reservationId + " under user: " + currentUser + "\n";
        }
        int cost = reservationResult.getInt("total_amount");
        reservationResult.close();
        // check if user has enough money to pay for reservation
        getUserBalanceStmt.clearParameters();
        getUserBalanceStmt.setString(1, currentUser);
        ResultSet userBalanceResult = getUserBalanceStmt.executeQuery();
        if (!userBalanceResult.next()) {
            userBalanceResult.close();
            getUserBalanceStmt.close();
            conn.rollback();
            conn.setAutoCommit(true);
            return "Failed to pay for reservation " + reservationId + "\n";
        }
        int userBalance = userBalanceResult.getInt("balance");
        userBalanceResult.close();
        if (userBalance < cost) {
            conn.rollback();
            conn.setAutoCommit(true);
            return "User has only " + userBalance + " in account but itinerary costs " + cost + "\n";
        }
        // update the user's balance (ie: subtract the price of the itinerary from the user's balance)
        updateUserBalanceStmt.clearParameters();
        updateUserBalanceStmt.setInt(1, cost);
        updateUserBalanceStmt.setString(2, currentUser);
        int updateUserBalanceResult = updateUserBalanceStmt.executeUpdate();
        if (updateUserBalanceResult != 1) {
            conn.rollback();
            conn.setAutoCommit(true);
            return "Failed to pay for reservation " + reservationId + "\n";
        }
        // update the reservation is_paid field to true
        updateReservationStmt.clearParameters();
        updateReservationStmt.setInt(1, reservationId);
        int updateReservationResult = updateReservationStmt.executeUpdate();
        if (updateReservationResult != 1) {
            conn.rollback();
            conn.setAutoCommit(true);
            return "Failed to pay for reservation " + reservationId + "\n";
        }
        int remainingBalance = userBalance - cost;
        conn.commit();
        conn.setAutoCommit(true);
        return "Paid reservation: " + reservationId + " remaining balance: " + remainingBalance + "\n";
    } catch (SQLException e) {
        try {
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // Retry for deadlock
        if (isDeadlock(e)) {
            return transaction_pay(reservationId);
        }
        e.printStackTrace();
        return "Failed to pay for reservation " + reservationId + "\n";
    }
}

  /* See QueryAbstract.java for javadoc */
  public String transaction_reservations() {
    // Check if user is logged in
    if (currentUser == null) {
      return "Cannot view reservations, not logged in\n";
    }
    StringBuilder sb = new StringBuilder();
    try {
        getReservationsStmt.clearParameters();
        getReservationsStmt.setString(1, currentUser);
        ResultSet reservationsResult = getReservationsStmt.executeQuery();
        boolean hasReservations = false;
        while (reservationsResult.next()) {
          hasReservations = true;
          int reservationId = reservationsResult.getInt("reservation_id");
          boolean isPaid = reservationsResult.getBoolean("is_paid");
          int f1_id = reservationsResult.getInt("f1_id");
          int f1_day = reservationsResult.getInt("f1_day");
          String f1_carrier = reservationsResult.getString("f1_carrier");
          String f1_num = reservationsResult.getString("f1_num");
          String f1_origin = reservationsResult.getString("f1_origin");
          String f1_dest = reservationsResult.getString("f1_dest");
          int f1_time = reservationsResult.getInt("f1_time");
          int f1_capacity = reservationsResult.getInt("f1_capacity");
          int f1_price = reservationsResult.getInt("f1_price");
          Flight flight1 = new Flight(
              f1_id, f1_day, f1_carrier, f1_num, f1_origin, f1_dest,
              f1_time, f1_capacity, f1_price
          );
          sb.append("Reservation ").append(reservationId)
            .append(" paid: ").append(isPaid).append(":\n");
          sb.append(flight1.toString()).append("\n");
          if (reservationsResult.getObject("f2_id") != null) {
              int f2_id = reservationsResult.getInt("f2_id");
              int f2_day = reservationsResult.getInt("f2_day");
              String f2_carrier = reservationsResult.getString("f2_carrier");
              String f2_num = reservationsResult.getString("f2_num");
              String f2_origin = reservationsResult.getString("f2_origin");
              String f2_dest = reservationsResult.getString("f2_dest");
              int f2_time = reservationsResult.getInt("f2_time");
              int f2_capacity = reservationsResult.getInt("f2_capacity");
              int f2_price = reservationsResult.getInt("f2_price");
              Flight flight2 = new Flight(
                  f2_id, f2_day, f2_carrier, f2_num, f2_origin, f2_dest, 
                  f2_time, f2_capacity, f2_price
              );
              sb.append(flight2.toString()).append("\n");
          }
      }
      reservationsResult.close();
      if (!hasReservations) {
          return "No reservations found\n";
      }
      return sb.toString();
    } catch (SQLException e) {
        e.printStackTrace();
        return "Failed to retrieve reservations\n";
    }
  }

  /**
   * Helper method to get the amount of people who have reserved
   * seats for a flight given the flight id (fid)
   */
  private int checkFlightAmountReservations(int fid) throws SQLException {
    flightCapacityStmt.clearParameters();
    flightAmountStmt.setInt(1, fid);
    flightAmountStmt.setInt(2, fid);
    ResultSet results = flightAmountStmt.executeQuery();
    results.next();
    int numReserved = results.getInt("num_reserved");
    results.close();
    return numReserved;
  }

  /**
   * Helper method to get the next reservation ID
   */
  private int nextReservationID() throws SQLException {
    maximumReservationIDStmt.clearParameters();
    ResultSet results = maximumReservationIDStmt.executeQuery();
    results.next();
    int nextResID = results.getInt("max_id") + 1;
    results.close();
    return nextResID;
  }

  /**
   * Example utility function that uses prepared statements
   */
  private int checkFlightCapacity(int fid) throws SQLException {
    flightCapacityStmt.clearParameters();
    flightCapacityStmt.setInt(1, fid);
    ResultSet results = flightCapacityStmt.executeQuery();
    results.next();
    int capacity = results.getInt("capacity");
    results.close();
    return capacity;
  }

  /**
   * Utility function to determine whether an error was caused by a deadlock
   */
  private static boolean isDeadlock(SQLException e) {
    return "40001".equals(e.getSQLState()) || "40P01".equals(e.getSQLState());
  }

  /**
   * A class to store information about an itinerary
   */
  class Itinerary implements Comparable<Itinerary> {
    List<Flight> flights;
    int totalTime;
    private int itineraryId;
    int totalPrice;

    public Itinerary(List<Flight> flights, int totalTime) {
        this.flights = flights;
        this.totalTime = totalTime;
        this.itineraryId = -1;
        for (int i = 0; i < flights.size(); i++) {
          this.totalPrice += flights.get(i).price;
        }
    }

    public void setItineraryId(int id) {
        this.itineraryId = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Itinerary ").append(itineraryId).append(": ")
          .append(flights.size()).append(" flight(s), ")
          .append(totalTime).append(" minutes\n");
        for (Flight flight : flights) {
            sb.append("ID: ").append(flight.fid)
              .append(" Day: ").append(flight.dayOfMonth)
              .append(" Carrier: ").append(flight.carrierId)
              .append(" Number: ").append(flight.flightNum)
              .append(" Origin: ").append(flight.originCity)
              .append(" Dest: ").append(flight.destCity)
              .append(" Duration: ").append(flight.time)
              .append(" Capacity: ").append(flight.capacity)
              .append(" Price: ").append(flight.price)
              .append("\n");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Itinerary other) {
        if (this.totalTime != other.totalTime) {
            return this.totalTime - other.totalTime;
        }
        int firstFidComparison = Integer.compare(this.flights.get(0).fid, other.flights.get(0).fid);
        if (firstFidComparison != 0) {
            return firstFidComparison;
        }
        if (this.flights.size() > 1 && other.flights.size() > 1) {
            return Integer.compare(this.flights.get(1).fid, other.flights.get(1).fid);
        }
        return this.flights.size() - other.flights.size();
    }
  }

  /**
   * A class to store information about a single flight
   */
  class Flight {
    public int fid;
    public int dayOfMonth;
    public String carrierId;
    public String flightNum;
    public String originCity;
    public String destCity;
    public int time;
    public int capacity;
    public int price;

    Flight(int id, int day, String carrier, String fnum, String origin, String dest, int tm,
           int cap, int pri) {
      fid = id;
      dayOfMonth = day;
      carrierId = carrier;
      flightNum = fnum;
      originCity = origin;
      destCity = dest;
      time = tm;
      capacity = cap;
      price = pri;
    }
    @Override
    public String toString() {
      return "ID: " + fid + " Day: " + dayOfMonth + " Carrier: " + carrierId + " Number: "
          + flightNum + " Origin: " + originCity + " Dest: " + destCity + " Duration: " + time
          + " Capacity: " + capacity + " Price: " + price;
    }
  }
}
