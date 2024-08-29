package com.dtu.trading_server.controller;

import com.dtu.trading_server.entity.Coin;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Watchlist;
import com.dtu.trading_server.service.CoinService;
import com.dtu.trading_server.service.UserService;
import com.dtu.trading_server.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Long userId = userService.findProfileByJwt(jwt).getId();
        Watchlist watchlist = watchlistService.findUserWatchlist(userId);

        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlist(
            @PathVariable Long watchlistId
    ) throws Exception {
        Watchlist watchlist = watchlistService.findById(watchlistId);

        return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addCoinToWatchlist(
            @PathVariable String coinId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);

        return ResponseEntity.ok(addedCoin);
    }
}
