package com.dtu.treading_server.controller;

import com.dtu.treading_server.entity.Coin;
import com.dtu.treading_server.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam int page) throws Exception {
        List<Coin> coins = coinService.getCoinList(page);
        return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(
            @PathVariable String coinId,
            @RequestParam int days
    ) throws Exception {
        String response = coinService.getMarketChart(coinId, days);

        JsonNode jsonNode = objectMapper.readTree(response);

        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception {
        String response = coinService.searchCoin(keyword);

        JsonNode jsonNode = objectMapper.readTree(response);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50Coins() throws Exception {
        String response = coinService.getTop50CoinsByMarketCapRank();

        JsonNode jsonNode = objectMapper.readTree(response);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/treading")
    ResponseEntity<JsonNode> getTreadingCoins() throws Exception {
        String response = coinService.getTreadingCoins();

        JsonNode jsonNode = objectMapper.readTree(response);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String response = coinService.getCoinDetails(coinId);

        JsonNode jsonNode = objectMapper.readTree(response);

        return ResponseEntity.ok(jsonNode);
    }
}
