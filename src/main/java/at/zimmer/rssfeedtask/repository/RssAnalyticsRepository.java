package at.zimmer.rssfeedtask.repository;

import at.zimmer.rssfeedtask.model.RssAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssAnalyticsRepository extends JpaRepository<RssAnalytics, Long> {
}
