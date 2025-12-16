package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Admin;
import model.Courier;
import model.User;

public class ProfileHandler {

    private Connect db = Connect.getInstance();

    public boolean updateProfile(User user) {
        StringBuilder query = new StringBuilder("UPDATE Users SET fullName=?, email=?, password=?, phone=?, address=?, gender=?");
        
        if (user instanceof Admin) {
            query.append(", emergencyContact=?");
        } else if (user instanceof Courier) {
            query.append(", vehicleType=?, vehiclePlate=?");
        }
        
        query.append(" WHERE idUser=?");

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getGender());
            
            int paramIndex = 7;

            if (user instanceof Admin) {
                ps.setString(paramIndex++, ((Admin) user).getEmergencyContact());
            } else if (user instanceof Courier) {
                ps.setString(paramIndex++, ((Courier) user).getVehicleType());
                ps.setString(paramIndex++, ((Courier) user).getVehiclePlate());
            }

            // Set WHERE Clause (ID User)
            ps.setString(paramIndex, user.getIdUser());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}