import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        // 2
        // 12:00:00.100 0.400,12:00:00.200 0.500,12:00:00.300 0.100,12:00:00.400 0.600,12:00:00.500 0.200,12:00:00.600 0.400

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine(), ",");
        int logSize = stk.countTokens();
        String[] logs = new String[logSize];

        for(int i = 0; i < logSize; i++) {
            logs[i] = stk.nextToken();
        }
        int numServer  = 2;

        solution(numServer, logs);
    }

    public static void solution(int numServer, String... logs) throws Exception {
        List<LocalTime> timeList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        List<Double> millisecondList = new ArrayList<>();

        Arrays.stream(logs).forEach( log -> {
            StringTokenizer stk = new StringTokenizer(log, " ");

            String strTime = stk.nextToken();
            System.out.println(strTime);
            timeList.add(LocalTime.parse(strTime, dtf));

            millisecondList.add(Double.valueOf(stk.nextToken()));
        });

        System.out.println("z");
    }

}
