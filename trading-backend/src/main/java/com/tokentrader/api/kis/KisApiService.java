package com.tokentrader.api.kis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokentrader.config.ApiConfig;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KisApiService {

    private final ApiConfig apiConfig;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Bucket kisApiBucket;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public Map<String, Object> getMarketData(String symbol) {
        // Rate limiting
        kisApiBucket.tryConsume(1);

        String url = apiConfig.getKis().getBaseUrl() + "/uapi/domestic-stock/v1/quotations/inquire-price";

        Map<String, String> headers = getAuthHeaders();
        headers.put("tr_id", "FHKST01010100");

        Map<String, String> params = new HashMap<>();
        params.put("FID_COND_MRKT_DIV_CODE", "J");
        params.put("FID_INPUT_ISCD", symbol);

        try {
            String response = executeGet(url, headers, params);
            return objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            log.error("Failed to get market data for {}", symbol, e);
            throw new RuntimeException("Failed to get market data", e);
        }
    }

    public String placeOrder(String symbol, String action, int quantity, double price) {
        kisApiBucket.tryConsume(1);

        String url = apiConfig.getKis().getBaseUrl() + "/uapi/domestic-stock/v1/trading/order-cash";

        Map<String, String> headers = getAuthHeaders();
        headers.put("tr_id", "buy".equals(action) ? "TTTC0802U" : "TTTC0801U");

        Map<String, Object> body = new HashMap<>();
        body.put("CANO", apiConfig.getKis().getAccountNumber());
        body.put("ACNT_PRDT_CD", apiConfig.getKis().getAccountProductCode());
        body.put("PDNO", symbol);
        body.put("ORD_DVSN", "01"); // 시장가
        body.put("ORD_QTY", String.valueOf(quantity));
        body.put("ORD_UNPR", String.valueOf((int) price));

        try {
            String response = executePost(url, headers, body);
            Map<String, Object> result = objectMapper.readValue(response, Map.class);
            return (String) ((Map) result.get("output")).get("ODNO");
        } catch (Exception e) {
            log.error("Failed to place order", e);
            throw new RuntimeException("Failed to place order", e);
        }
    }

    public Map<String, Object> getAccountInfo() {
        kisApiBucket.tryConsume(1);

        String url = apiConfig.getKis().getBaseUrl() + "/uapi/domestic-stock/v1/trading/inquire-balance";

        Map<String, String> headers = getAuthHeaders();
        headers.put("tr_id", "TTTC8434R");

        Map<String, String> params = new HashMap<>();
        params.put("CANO", apiConfig.getKis().getAccountNumber());
        params.put("ACNT_PRDT_CD", apiConfig.getKis().getAccountProductCode());
        params.put("AFHR_FLPR_YN", "N");
        params.put("OFL_YN", "N");
        params.put("INQR_DVSN", "01");
        params.put("UNPR_DVSN", "01");
        params.put("FUND_STTL_ICLD_YN", "N");
        params.put("FNCG_AMT_AUTO_RDPT_YN", "N");
        params.put("PRCS_DVSN", "01");
        params.put("CTX_AREA_FK100", "");
        params.put("CTX_AREA_NK100", "");

        try {
            String response = executeGet(url, headers, params);
            return objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            log.error("Failed to get account info", e);
            throw new RuntimeException("Failed to get account info", e);
        }
    }

    public Map<String, Object> getPositions() {
        // 보유 종목 조회
        return getAccountInfo(); // 계좌 정보에 포함됨
    }

    private Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("appkey", apiConfig.getKis().getAppKey());
        headers.put("appsecret", apiConfig.getKis().getSecretKey());
        headers.put("tr_cont", "");
        return headers;
    }

    private String executeGet(String url, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        params.forEach(urlBuilder::addQueryParameter);

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build());
        headers.forEach(requestBuilder::header);

        try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }
            return response.body().string();
        }
    }

    private String executePost(String url, Map<String, String> headers, Map<String, Object> body)
            throws IOException {
        String json = objectMapper.writeValueAsString(body);
        RequestBody requestBody = RequestBody.create(json, JSON);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);
        headers.forEach(requestBuilder::header);

        try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }
            return response.body().string();
        }
    }
}