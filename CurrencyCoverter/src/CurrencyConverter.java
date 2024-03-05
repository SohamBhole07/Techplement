import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {

        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
        ArrayList<String> fav_currency = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");

        Integer from , to;
        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Currency Converter!");

        System.out.println("Do you wish to 1:add , 2:update or 3:view your favourite currency list");
        int fav = sc.nextInt();

        if(fav == 1){
            System.out.println("Enter your favourite currency");
            String line = reader.readLine();
            fav_currency.add(line);
        }else if(fav == 2){
            System.out.println(("Enter currency you want to remove"));
            String line = reader.readLine();
            fav_currency.remove(line);
        }else if(fav == 3){
            for(String s : fav_currency){
                System.out.println(s);
            }
        }


        System.out.println("Currency converting FROM ?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar) \t 5:INR(Indian Rupee)");
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converting TO ?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar) \t 5:INR(Indian Rupee)");
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("Amount wish to convert");
        amount = sc.nextFloat();

        sendHttpRequest(fromCode , toCode , amount);

        System.out.println("Thank you for using Currency converter");
    }
    public static void sendHttpRequest(String fromCode , String toCode , double amount) throws IOException {

        DecimalFormat f = new DecimalFormat(  "00.00");
       String GET_URL = "https://v6.exchangerate-api.com/v6/2b278371b225e473b850a130/latest/USD?base=" + toCode + "&symbols=" + fromCode;
       URL url = new URL(GET_URL);
       HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
       httpURLConnection.setRequestMethod("GET");
       int responseCode = httpURLConnection.getResponseCode();
       System.out.println(responseCode);

       if (responseCode ==  HttpURLConnection.HTTP_OK) {
           BufferedReader in = new BufferedReader((new InputStreamReader(httpURLConnection.getInputStream())));
           String inputLine;
           StringBuffer response = new StringBuffer();

           while((inputLine = in.readLine()) != null) {
               response.append(inputLine);
           }in.close();

           JSONObject obj = new JSONObject(response.toString());
           Double exchangeRate = obj.getJSONObject("conversion_rates").getDouble(toCode);
           //System.out.println(obj.getJSONObject("conversion_rates"));
           System.out.println(exchangeRate);
           System.out.println();
           System.out.println(f.format(amount) + fromCode + " = " + f.format(amount* exchangeRate) + toCode);
        }
        else{
            System.out.println("Get request failed");
       }


    }







}
