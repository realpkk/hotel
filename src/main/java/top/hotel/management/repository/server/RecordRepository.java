package top.hotel.management.repository.server;

import org.springframework.stereotype.Repository;
import top.hotel.management.entity.server.Record;
import top.hotel.management.repository.base.AbstractRepository;

@Repository
public interface RecordRepository extends AbstractRepository <Record,Long> {

    Record findRecordByRecordCode(String recordCode);
}
