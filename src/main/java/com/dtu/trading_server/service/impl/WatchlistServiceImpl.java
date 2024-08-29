package com.dtu.trading_server.service.impl;

import com.dtu.trading_server.entity.Coin;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Watchlist;
import com.dtu.trading_server.repository.WatchlistRepository;
import com.dtu.trading_server.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist == null) {
            throw new Exception("Watchlist not found");
        }

        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);

        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> optionalWatchlist = watchlistRepository.findById(id);
        if (optionalWatchlist.isEmpty()) {
            throw new Exception("Watchlist not found");
        }

        return optionalWatchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) {
        Watchlist watchlist = watchlistRepository.findByUserId(user.getId());
        if (watchlist.getCoins().contains(coin)) watchlist.getCoins().remove(coin);
        else watchlist.getCoins().add(coin);

        watchlistRepository.save(watchlist);
        return coin;
    }
}
