package ee.bitweb.transactions.domain.detector.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorRepository extends JpaRepository<Detector, Long> {
}
