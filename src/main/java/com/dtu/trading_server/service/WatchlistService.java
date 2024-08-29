package com.dtu.trading_server.service;

import com.dtu.trading_server.entity.Coin;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user);
}
