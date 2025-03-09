<%@ page import="java.sql.*" %>
<%@ page import="com.joancortes.aplicacioweb.db.Connexio" %>
<!DOCTYPE html>
<html>
<head>
    <title>Llibres - La Meva Llibreria</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Llistat de Llibres</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Títol</th>
            <th>Autor</th>
            <th>Preu</th>
        </tr>
        <%
            /**
             * Aquest bloc de codi es connecta a la base de dades per recuperar
             * la informació dels llibres i mostrar-la a la taula HTML.
             * 
             * - Connexió amb la base de dades mitjançant la classe Connexio.
             * - Consulta SQL per obtenir els llibres de la taula `llibres`.
             * - Iteració dels resultats de la consulta per mostrar cada llibre a la taula.
             * - En cas d'error, es mostra un missatge d'error a la taula.
             */
            try (Connection conn = Connexio.getConnexio()) {
                // Consulta SQL per obtenir informació dels llibres
                String sql = "SELECT id, titol, isbn, any_publicacio FROM llibres";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    // Itera pels resultats de la consulta
                    while (rs.next()) {
        %>
                        <tr>
                            <td><%= rs.getInt("id") %></td>
                            <td><%= rs.getString("titol") %></td>
                            <td><%= rs.getString("isbn") %></td>
                            <td><%= rs.getDouble("any_publicacio") %></td>
                        </tr>
        <%
                    }
                }
            } catch (SQLException e) {
        %>
                <tr>
                    <!-- Mostra un missatge d'error si no es pot accedir a la base de dades -->
                    <td colspan="4" style="color: red;">Error en accedir a la base de dades: <%= e.getMessage() %></td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>

