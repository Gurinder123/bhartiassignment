package service;

import com.airtel.gurinder.domain.ScheduleTask;
import com.airtel.gurinder.domain.request.ScheduleRequestBodyDto;
import com.airtel.gurinder.domain.request.ScheduleRequestDto;
import com.airtel.gurinder.domain.request.ScheduleUpdateRequestBody;
import com.airtel.gurinder.domain.response.ScheduleResponseDto;
import com.airtel.gurinder.repository.ScheduleTaskRepository;
import com.airtel.gurinder.service.ScheduledService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by gurinder on 23/8/16.
 */
public class ScheduledServiceTest {

    @Mock
    private ScheduleTaskRepository scheduleTaskRepository;

    @InjectMocks
    private ScheduledService service = new ScheduledService(scheduleTaskRepository);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsCreatingNewTask() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        when(scheduleTaskRepository.save(Mockito.any(ScheduleTask.class))).thenReturn(task);
        Optional<ScheduleResponseDto> data = service.createNewTask(dto, "data");
        ScheduleResponseDto scheduleResponseDto = data.get();
        assertEquals(dto.getUrl(), scheduleResponseDto.getScheduleResponseBodyDto().getUrl());
       /* ArgumentCaptor Example for verifying ScheduleTask created by new*/
        ArgumentCaptor<ScheduleTask> captor = ArgumentCaptor.forClass(ScheduleTask.class);
        Mockito.verify(scheduleTaskRepository).save(captor.capture());
        ScheduleTask scheduleTask = captor.getValue();
        assertEquals("http://www.google.com", scheduleTask.getUrl());
    }

    @Test
    public void testIsFindingTheTaskById() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        when(scheduleTaskRepository.findOne(1)).thenReturn(task);
        Optional<ScheduleResponseDto> data = service.findById(1);
        ScheduleResponseDto scheduleResponseDto = data.get();
        assertEquals(dto.getUrl(), scheduleResponseDto.getScheduleResponseBodyDto().getUrl());
    }

    @Test
    public void testIsFindingTheTaskByIdNotExist() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        when(scheduleTaskRepository.findOne(1)).thenReturn(task);
        Optional<ScheduleResponseDto> data = service.findById(2);
        assertEquals(false, data.isPresent());
    }

    @Test
    public void testIfDeletingById() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        when(scheduleTaskRepository.findOne(1)).thenReturn(task);
        boolean status = service.deleteById(1);
        assertEquals(true, status);
    }

    @Test
    public void testIfDeletingByIdAndRecordNotExist() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        when(scheduleTaskRepository.findOne(1)).thenReturn(null);
        boolean status = service.deleteById(1);
        assertEquals(false, status);
    }

    @Test
    public void testTheUpDateTaskInfo() {
        ScheduleRequestDto dto = scheduleRequestDtoDummyFactory();
        ScheduleTask task = scheduleTaskDummyFactory(dto);
        ScheduleUpdateRequestBody requestBody = scheduleUpdateRequestBodyDummyFactory();
        when(scheduleTaskRepository.findOne(1)).thenReturn(task);
        when(scheduleTaskRepository.save(Mockito.any(ScheduleTask.class))).thenReturn(task);
        Optional<ScheduleResponseDto> data = service.upDateTaskInfo(1, requestBody);
        ScheduleResponseDto scheduleResponseDto = data.get();
        assertEquals(new Integer(10), scheduleResponseDto.getScheduleResponseBodyDto().getId());
    }

    private ScheduleUpdateRequestBody scheduleUpdateRequestBodyDummyFactory() {
        ScheduleUpdateRequestBody updateRequestBody = new ScheduleUpdateRequestBody();
        updateRequestBody.setCallBackAt("2013-10-04T05:34:28+05:30");
        return updateRequestBody;
    }

    private ScheduleTask scheduleTaskDummyFactory(ScheduleRequestDto dto) {
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setUrl(dto.getUrl());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            scheduleTask.setCallBackAt(formatter.parse(dto.getCallBackAt().substring(0, dto.getCallBackAt().length() - 6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduleTask.setRequestMethod(dto.getRequestMethod());
        scheduleTask.setRequestBody(dto.getRequestBody());
        scheduleTask.setAuthToken("token");
        scheduleTask.setRetryCount(new Integer(1));
        scheduleTask.setCreatedAt(new Date());
        scheduleTask.setCallBackAt(new Date());
        return scheduleTask;
    }

    private ScheduleRequestDto scheduleRequestDtoDummyFactory() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setUrl("http://www.google.com");
        dto.setReferenceId("123");
        dto.setRequestMethod("GET");
        dto.setCallBackAt("2013-10-04T05:34:28+05:30");
        dto.setRequestBody(new ScheduleRequestBodyDto());
        return dto;
    }
}
