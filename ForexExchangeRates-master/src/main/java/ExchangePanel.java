import org.jsoup.nodes.Document;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ExchangePanel extends JPanel {
    private Image background;
    private JLabel coinValueLabel, afterCalculate, coinValueText;
    private JTextField amountOfMoney;
    private JButton resetButton, calculateButton;
    private JPanel currencyPanel;
    private CurrencyFetcher currencyFetcher;
    private String currentCurrencyCode;

    public ExchangePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        initializeBackground(width, height);
        initializeComponents();
    }

    private void initializeBackground(int width, int height) {
        try {
            Image originalImage = ImageIO.read(new URL("https://ideas.ted.com/wp-content/uploads/sites/3/2016/06/sharing_economy.jpg?w=750"));
            background = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            background = new ImageIcon("background.jpg").getImage();
        }

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                try {
                    Image originalImage = ImageIO.read(new URL("https://ideas.ted.com/wp-content/uploads/sites/3/2016/06/sharing_economy.jpg?w=750"));
                    background = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    background = new ImageIcon("background.jpg").getImage();
                }
            }
        });
    }

    private void initializeComponents() {
        currencyFetcher = new CurrencyFetcher();

        // Initialize currency panel
        currencyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        currencyPanel.setOpaque(false);
        add(currencyPanel, BorderLayout.NORTH);

        String[] currencyCodes = {"USD/ILS", "EUR/ILS", "EUR/USD", "EUR/GBP", "GBP/ILS"};
        for (String code : currencyCodes) {
            createCurrencyButton(code);
        }

        // Initialize center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components to center panel
        initializeCenterPanelComponents(centerPanel, gbc);

        // Initialize button panel
        initializeButtonPanel();

        setComponentVisibility(false);
    }

    private void initializeCenterPanelComponents(JPanel centerPanel, GridBagConstraints gbc) {
        // Current Exchange Rate
        coinValueText = createStyledLabel("Exchange Rate:", gbc, 0, 0);
        centerPanel.add(coinValueText, gbc);

        coinValueLabel = createStyledLabel("", gbc, 1, 0);
        centerPanel.add(coinValueLabel, gbc);

        // Amount Input
        JLabel amountLabel = createStyledLabel("Amount:", gbc, 0, 1);
        centerPanel.add(amountLabel, gbc);

        amountOfMoney = new JTextField(10);
        amountOfMoney.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(amountOfMoney, gbc);

        // Result
        afterCalculate = createStyledLabel("", gbc, 0, 2);
        gbc.gridwidth = 2;
        centerPanel.add(afterCalculate, gbc);
    }

    private JLabel createStyledLabel(String text, GridBagConstraints gbc, int x, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        return label;
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);

        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.addActionListener(e -> calculateExchange());
        buttonPanel.add(calculateButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(e -> reset());
        buttonPanel.add(resetButton);
    }

    private void createCurrencyButton(String currencyCode) {
        JButton button = new JButton(currencyCode);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(e -> handleCurrencySelection(currencyCode, e));
        currencyPanel.add(button);
    }

    private void handleCurrencySelection(String currencyCode, java.awt.event.ActionEvent e) {
        if (currentCurrencyCode != null) {
            currencyFetcher.stopFetching();
        }
        
        setComponentVisibility(true);
        currentCurrencyCode = currencyCode;
        
        // Update the coinValueText to show the currency pair
        String[] currencies = currencyCode.split("/");
        coinValueText.setText(String.format("1 %s = ", currencies[0]));
        
        currencyFetcher.startFetching(currencyCode, coinValueLabel, this);
        
        // Hide other currency buttons
        for (Component comp : currencyPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setVisible(comp == e.getSource());
            }
        }
    }

    private void setComponentVisibility(boolean visible) {
        coinValueLabel.setVisible(visible);
        coinValueText.setVisible(visible);
        amountOfMoney.setVisible(visible);
        afterCalculate.setVisible(visible);
        calculateButton.setVisible(visible);
        resetButton.setVisible(visible);
    }

    private void calculateExchange() {
        try {
            if (currentCurrencyCode == null || amountOfMoney.getText().isEmpty()) {
                afterCalculate.setText("Please select a currency and enter an amount");
                return;
            }

            double amount = Double.parseDouble(amountOfMoney.getText());
            if (amount < 0) {
                afterCalculate.setText("Please enter a positive amount");
                return;
            }

            String rateText = coinValueLabel.getText().trim();
            if (rateText.isEmpty() || rateText.equals("Rate not available")) {
                afterCalculate.setText("Exchange rate not available");
                return;
            }

            double rate = Double.parseDouble(rateText);
            double result = amount * rate;

            // Get the target currency from the pair
            String targetCurrency = currentCurrencyCode.split("/")[1];
            afterCalculate.setText(String.format("%.2f %s", result, targetCurrency));

        } catch (NumberFormatException ex) {
            afterCalculate.setText("Invalid input - please enter a valid number");
        } catch (Exception ex) {
            afterCalculate.setText("Error in calculation");
        }
    }

    private void reset() {
        if (currentCurrencyCode != null) {
            currencyFetcher.stopFetching();
            currentCurrencyCode = null;
        }
        
        setComponentVisibility(false);
        amountOfMoney.setText("");
        coinValueLabel.setText("");
        afterCalculate.setText("");
        
        for (Component comp : currencyPanel.getComponents()) {
            comp.setVisible(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
    }
}