import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashMap;
import java.util.Map;

public class CurrencyFetcher {
    private final AtomicBoolean isRunning;
    private Thread fetcherThread;
    private Instant lastFetchTime;
    private static final int MIN_FETCH_INTERVAL_SECONDS = 300; // 5 minutes
    private final Map<String, Double> rateCache;
    private final Map<String, Double> exchangeRates;

    public CurrencyFetcher() {
        this.isRunning = new AtomicBoolean(false);
        this.lastFetchTime = Instant.EPOCH;
        this.rateCache = new HashMap<>();
        this.exchangeRates = new HashMap<>();
        initializeExchangeRates();
    }

    private void initializeExchangeRates() {
        // Initialize with base rates (you can update these values)
        exchangeRates.put("USD/ILS", 3.65);
        exchangeRates.put("EUR/ILS", 3.95);
        exchangeRates.put("EUR/USD", 1.08);
        exchangeRates.put("EUR/GBP", 0.85);
        exchangeRates.put("GBP/ILS", 4.65);
    }

    public void startFetching(String currencyPair, JLabel coinValueLabel, JPanel panel) {
        stopFetching();

        String[] currencies = currencyPair.split("/");
        if (currencies.length != 2) {
            updateLabel(coinValueLabel, "Invalid currency pair format", panel);
            return;
        }

        String fromCurrency = currencies[0].trim();
        String toCurrency = currencies[1].trim();

        isRunning.set(true);
        fetcherThread = new Thread(() -> {
            while (isRunning.get()) {
                try {
                    fetchAndUpdateRate(currencyPair, coinValueLabel, panel);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    handleError(e, coinValueLabel, panel);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
        fetcherThread.setDaemon(true);
        fetcherThread.start();
    }

    private void fetchAndUpdateRate(String currencyPair, JLabel coinValueLabel, JPanel panel) {
        Double rate = exchangeRates.get(currencyPair);
        if (rate != null) {
            String formattedRate = String.format("%.4f", rate);
            rateCache.put(currencyPair, rate);
            updateLabel(coinValueLabel, formattedRate, panel);
        } else {
            // Try to calculate reverse rate
            String[] currencies = currencyPair.split("/");
            String reversePair = currencies[1] + "/" + currencies[0];
            Double reverseRate = exchangeRates.get(reversePair);
            
            if (reverseRate != null) {
                rate = 1 / reverseRate;
                String formattedRate = String.format("%.4f", rate);
                rateCache.put(currencyPair, rate);
                updateLabel(coinValueLabel, formattedRate, panel);
            } else {
                updateLabel(coinValueLabel, "Rate not available", panel);
            }
        }
    }

    private void handleError(Exception e, JLabel label, JPanel panel) {
        String message = "Error: ";
        if (e instanceof IOException) {
            message += "Network error. Retrying...";
        } else {
            message += e.getMessage();
        }
        updateLabel(label, message, panel);
    }

    public void stopFetching() {
        isRunning.set(false);
        if (fetcherThread != null && fetcherThread.isAlive()) {
            fetcherThread.interrupt();
            try {
                fetcherThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateLabel(JLabel label, String text, JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            label.setText(text);
            panel.repaint();
        });
    }

    // Method to update exchange rates if needed
    public void updateExchangeRate(String currencyPair, double newRate) {
        exchangeRates.put(currencyPair, newRate);
    }

    // Method to get the current rate for a currency pair
    public double getRate(String currencyPair) {
        return exchangeRates.getOrDefault(currencyPair, 0.0);
    }
}