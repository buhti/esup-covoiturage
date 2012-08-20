package org.esupportail.covoiturage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.joda.time.DateTime;

public class GenerateStatTestData {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        StringBuffer sb = new StringBuffer();
        int iterations = 400;

        DateTime date = DateTime.now().minusDays(iterations);

        for (int i = 0; i < iterations; i++) {
            sb.append(createInsertStatement(StatType.LOGINS, date, random(30, 80)));
            sb.append(createInsertStatement(StatType.REGISTRATIONS, date, random(0, 10)));
            sb.append(createInsertStatement(StatType.ROUTES, date, random(0, 20)));
            sb.append(createInsertStatement(StatType.SEARCHES, date, random(30, 200)));
            date = date.plusDays(1);
        }

        BufferedWriter out = new BufferedWriter(new FileWriter("src/test/resources/stat-data.sql"));
        out.write(sb.toString());
        out.close();
    }

    private static StringBuffer createInsertStatement(StatType type, DateTime date, int value) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO Stat (stat_type, stat_date, stat_value) VALUES (");
        sb.append(type.ordinal());
        sb.append(", '");
        sb.append(date.toString());
        sb.append("', ");
        sb.append(value);
        sb.append(");\n");
        return sb;
    }

    private static int random(int min, int max) {
        Double num = Math.random() * (max - min) + min;
        return num.intValue();
    }

}
