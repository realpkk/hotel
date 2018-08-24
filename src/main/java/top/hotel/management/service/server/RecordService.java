package top.hotel.management.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hotel.management.entity.server.Record;
import top.hotel.management.repository.server.RecordRepository;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public  Record getCurrentRecord(String recordCode){
        return recordRepository.findRecordByRecordCode(recordCode);
    }

    public void createRecord(Record record) {
        recordRepository.saveAndFlush(record);
    }

    public void checkOut(String recordCode) {
        Record record = getCurrentRecord(recordCode);
        recordRepository.delete(record);
    }
}
