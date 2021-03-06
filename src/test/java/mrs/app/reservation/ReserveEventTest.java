package mrs.app.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.Test;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ReserveEventTest {
	private ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
			.modulesToInstall(new Jackson2HalModule(), new JavaTimeModule())
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

	@Test
	public void testReserveEvent() throws Exception {
		String json = objectMapper.writeValueAsString(new ReserveEvent("1_2016-12-20",
				LocalTime.of(9, 0), LocalTime.of(10, 0)));
		assertThat(json).isEqualTo(
				"{\"reservableRoomId\":\"1_2016-12-20\",\"startTime\":\"09:00:00\",\"endTime\":\"10:00:00\",\"type\":\"reserve\"}");
		ReserveEvent event = objectMapper.readValue(
				"{\"type\":\"reserve\",\"reservableRoomId\":\"1_2016-12-20\",\"startTime\":\"09:00:00\",\"endTime\":\"10:00:00\"}\n",
				ReserveEvent.class);
		assertThat(event).isInstanceOf(ReserveEvent.class);
		assertThat(((ReserveEvent) event).getReservableRoomId())
				.isEqualTo("1_2016-12-20");
		assertThat(((ReserveEvent) event).getStartTime()).isEqualTo(LocalTime.of(9, 0));
		assertThat(((ReserveEvent) event).getEndTime()).isEqualTo(LocalTime.of(10, 0));
	}
}