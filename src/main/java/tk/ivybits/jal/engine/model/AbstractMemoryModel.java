package tk.ivybits.jal.engine.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.LinkedList;

public abstract class AbstractMemoryModel implements MemoryModel {
    public AbstractMemoryModel(InputStream... is) throws Exception {
        initialize();
        for (InputStream str : is) {
            loadStream(str);
        }
    }

    protected abstract void initialize() throws Exception;

    public void loadStream(InputStream is) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line, last = " ";
        LinkedList<String> resp = new LinkedList<String>();

        while ((line = br.readLine()) != null) {
            if (line.isEmpty())
                continue;
            if (line.charAt(0) == '#') {
                if (line.length() == 1)
                    continue;
                if (!resp.isEmpty()) {
                    addResponse(last, resp.toArray(new String[0]));
                }
                resp = new LinkedList<String>();
                last = line.substring(1);
            } else
                resp.add(line);
        }
        br.close();

        if (!resp.isEmpty()) {
            addResponse(last, resp.toArray(new String[0]));
        }
    }

    public abstract void addResponse(String key, String[] responses);
}
