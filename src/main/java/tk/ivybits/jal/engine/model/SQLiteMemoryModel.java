package tk.ivybits.jal.engine.model;

import tk.ivybits.jal.engine.string.LexicalMatcher;

import java.io.InputStream;
import java.sql.*;

public class SQLiteMemoryModel extends AbstractMemoryModel {
    protected static final String STRING_SEPARATOR = "\0";
    protected Connection connection;

    public SQLiteMemoryModel(InputStream... is) throws Exception {
        super(is);
    }

    @Override
    protected void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS responses(key TEXT, resps TEXT)");
    }

    @Override
    public void addResponse(String key, String[] value) {
        String resp = "";
        for (int i = 0; i != value.length; i++) {
            resp += value[i] + STRING_SEPARATOR;
        }
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO responses VALUES (?, ?)");
            statement.setString(1, key);
            statement.setString(2, resp);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] search(String input) {
        input = input.trim();
        try {
            String[] words = input.split(" ");
            String query = "SELECT key, resps FROM responses WHERE" +
                    new String(new char[words.length - 1]).replace("\0", " key LIKE ? OR") + " key LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i != words.length; i++) {
                statement.setString(i + 1, String.format("%%%s%%", words[i]));
            }

            ResultSet set = statement.executeQuery();
            double n = 0.0;
            String closestKey = null;
            String[] closestReplies = null;
            while (set.next()) {
                String key = set.getString(1);
                double sim;
                if ((sim = LexicalMatcher.getSimilarity(input, key)) > n) {
                    closestKey = key;
                    closestReplies = set.getString(2).split(STRING_SEPARATOR);
                    n = sim;
                }
            }
            return closestKey != null ? closestReplies : new String[0];
        } catch (SQLException e) {
            System.err.println("SQLite lookup failed.");
            e.printStackTrace();
        }
        return new String[0];
    }
}
