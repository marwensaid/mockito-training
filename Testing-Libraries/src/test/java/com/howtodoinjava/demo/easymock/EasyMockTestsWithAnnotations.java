package com.howtodoinjava.demo.easymock;

import com.howtodoinjava.demo.systemUnderTest.Record;
import com.howtodoinjava.demo.systemUnderTest.RecordDao;
import com.howtodoinjava.demo.systemUnderTest.RecordService;
import com.howtodoinjava.demo.systemUnderTest.SequenceGenerator;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class EasyMockTestsWithAnnotations {
  @Mock
  RecordDao mockDao;

  @Mock
  SequenceGenerator mockGenerator;

  @TestSubject
  RecordService service = new RecordService(this.mockGenerator, this.mockDao);

  @Test
  public void testSaveRecord() {
    Record record = new Record();
    record.setName("Test Record");

    //Set expectations
    expect(this.mockGenerator.getNext()).andReturn(100L);
    expect(this.mockDao.saveRecord(EasyMock.anyObject(Record.class))).andReturn(record);

    //Replay
    replay(this.mockGenerator);
    replay(this.mockDao);

    //Test and assertions
    RecordService service = new RecordService(this.mockGenerator, this.mockDao);
    Record savedRecord = service.saveRecord(record);

    Assertions.assertEquals("Test Record", savedRecord.getName());
    Assertions.assertEquals(100L, savedRecord.getId());

    //Verify
    verify(this.mockGenerator);
    verify(this.mockDao);
  }
}
