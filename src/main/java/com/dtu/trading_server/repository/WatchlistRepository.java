package com.dtu.trading_server.repository;

import com.dtu.trading_server.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Watchlist findByUserId(Long userId);
}
