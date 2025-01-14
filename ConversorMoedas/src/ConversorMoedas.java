import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorMoedas {
    private static final String API_KEY = "816b35ed76c616da73894a9a";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Conversor de Moedas ===");
            System.out.println("1. Real para Dólar");
            System.out.println("2. Dólar para Real");
            System.out.println("3. Real para Euro");
            System.out.println("4. Dólar para Euro");
            System.out.println("5. Euro para Dólar");
            System.out.println("6. Euro para Real");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            
            if (opcao == 7) {
                System.out.println("Programa finalizado!");
                break;
            }
            
            System.out.print("Digite o valor a ser convertido: ");
            double valor = scanner.nextDouble();
            
            try {
                String moedaOrigem = "", moedaDestino = "";
                
                switch (opcao) {
                    case 1:
                        moedaOrigem = "BRL";
                        moedaDestino = "USD";
                        break;
                    case 2:
                        moedaOrigem = "USD";
                        moedaDestino = "BRL";
                        break;
                    case 3:
                        moedaOrigem = "BRL";
                        moedaDestino = "EUR";
                        break;
                    case 4:
                        moedaOrigem = "USD";
                        moedaDestino = "EUR";
                        break;
                    case 5:
                        moedaOrigem = "EUR";
                        moedaDestino = "USD";
                        break;
                    case 6:
                        moedaOrigem = "EUR";
                        moedaDestino = "BRL";
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        continue;
                }
                
                String urlStr = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaOrigem;
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                JSONObject jsonResponse = new JSONObject(response.toString());
                double taxa = jsonResponse.getJSONObject("conversion_rates").getDouble(moedaDestino);
                
                double resultado = valor * taxa;
                System.out.printf("%.2f %s = %.2f %s%n", valor, moedaOrigem, resultado, moedaDestino);
                
            } catch (Exception e) {
                System.out.println("Erro ao fazer a conversão: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
}
