package exchange.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hc.client5.http.fluent.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeRateFetcher {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateFetcher.class);

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new FetchTask(), 0, 60000); // Schedule task to run every minute
    }

    static class FetchTask extends TimerTask {

        @Override
        public void run() {
            try {
                String response = Request.get("https://api.exchangerate-api.com/v4/latest/JPY")
                        .execute()
                        .returnContent()
                        .asString();
                JSONObject json = new JSONObject(response);
                double jpyToHkd = json.getJSONObject("rates").getDouble("HKD");
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                logger.info("{}, 1 JPY = {} HKD", currentTime, jpyToHkd);
            } catch (Exception e) {
                logger.error("Error fetching exchange rate", e);
            }
        }
    }
}
