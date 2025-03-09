
package com.joancortes.aplicacioweb.servlets;

import com.joancortes.aplicacioweb.db.Connexio;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet per gestionar la consulta dels llibres a la base de dades.
 * Aquest servlet s'encarrega d'obtenir les dades dels llibres emmagatzemats a la base de dades
 * i mostrar-los en una taula HTML.
 */
@WebServlet("/consulta")
public class Consulta extends HttpServlet {

    /**
     * Mètode doGet per processar les sol·licituds GET.
     * Aquest mètode consulta la base de dades per obtenir la informació dels llibres
     * i mostra la informació en format HTML.
     *
     * @param request La sol·licitud HTTP realitzada pel client.
     * @param response La resposta HTTP que es retorna al client.
     * @throws ServletException Si ocorre un error de servlet.
     * @throws IOException Si ocorre un error d'entrada/sortida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configura el tipus de contingut de la resposta a HTML
        response.setContentType("text/html");
        
        // Obté l'objecte PrintWriter per escriure la resposta HTML
        PrintWriter out = response.getWriter();
        
        try (Connection conn = Connexio.getConnexio()) {
            // Consulta SQL per obtenir tots els llibres
            String query = "SELECT * FROM llibres"; 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // Comença a escriure la taula HTML per mostrar els resultats
            out.println("<html><body>");
            out.println("<h1>Dades de la base de dades:</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Nom</th><th>Descripció</th></tr>"); 
            
            // Itera sobre els resultats de la consulta i escriu cada llibre en una fila de la taula
            while (rs.next()) {
                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String isbn = rs.getString("isbn");
                
                // Escriu una nova fila a la taula amb la informació del llibre
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + titol + "</td>");
                out.println("<td>" + isbn + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (Exception e) {
            // Si hi ha un error en la consulta a la base de dades, es mostra un missatge d'error
            out.println("<html><body>");
            out.println("<h2>Error al consultar la base de dades: " + e.getMessage() + "</h2>");
            out.println("</body></html>");
        } finally {
            // Tanca el PrintWriter després d'escriure la resposta
            out.close();
        }
    }
}
